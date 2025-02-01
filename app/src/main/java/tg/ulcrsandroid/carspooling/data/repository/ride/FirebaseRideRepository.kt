package tg.ulcrsandroid.carspooling.data.repository.ride

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import tg.ulcrsandroid.carspooling.core.models.ReservationModel
import tg.ulcrsandroid.carspooling.core.models.RideModel

class FirebaseRideRepository(db: FirebaseFirestore) : RideRepository {
    private val db = db

    override suspend fun add(rideModel: RideModel, onResult: (Boolean) -> Unit) {
        return try {
            db.collection("rides").add(rideModel).await()
            onResult(true)
        } catch (e: Exception) {
            onResult(false)
        }
    }

    override suspend fun delete(rideId: String, onResult: (Boolean) -> Unit) {
        try {
            // Rechercher le document avec whereEqualTo
            val querySnapshot = db.collection("rides").whereEqualTo("rideId", rideId).get().await()

            // Vérifier si un document correspondant a été trouvé
            if (!querySnapshot.isEmpty) {
                // Supprimer chaque document trouvé (même si normalement il ne doit y en avoir qu'un)
                for (document in querySnapshot.documents) {
                    db.document(document.id).delete().await()
                }
                onResult(true)
            } else {
                onResult(false)
            }
        } catch (e: Exception) {
            onResult(false)
        }
    }

    override suspend fun search(departure: String, arrival: String,onResult: (List<RideModel>) -> Unit) {
        return try {
            val querySnapshot = db.collection("rides").whereEqualTo("departure", departure)
                .whereEqualTo("arrival", arrival)
                .get()
                .await() // await pour attendre les résultats de la requête

            // Transforme les documents en objets RideModel
            val rides: List<RideModel> = querySnapshot.documents.mapNotNull { document ->
                document.toObject(RideModel::class.java) // Conversion Firestore
            }

            onResult(rides) // Retourne la liste des résultats
        } catch (e: Exception) {
            onResult(emptyList()) // Retourne une liste vide en cas d'échec
        }
    }

    override suspend fun reservation(reservation: ReservationModel, onResult: (Boolean) -> Unit) {
        return try {
            db.collection("reservations").add(reservation).await()
            onResult(true)
        } catch (e: Exception) {
            onResult(false)
        }
    }

    override suspend fun update(reservationId: String, status: String, onResult: (Boolean) -> Unit) {
        return try {
            val querySnapshot = db.collection("reservations").whereEqualTo("reservationId", reservationId).get().await()
            querySnapshot.documents.mapNotNull {document ->
                document.reference.update("status", status).await()
            }
            onResult(true)
        } catch (e: Exception) {
            onResult(false)
        }
    }

    override suspend fun getRide(driverId: String, onResult: (List<RideModel>) -> Unit) {
        return try {
            val querySnapshot = db.collection("rides").whereEqualTo("driverId", driverId).get().await()
            val rides = querySnapshot.documents.mapNotNull { document ->
                document.toObject(RideModel::class.java)
            }
            onResult(rides)
        } catch (e: Exception) {
            onResult(emptyList())
        }
    }

    override suspend fun getReservationPassenger(
        passengerId: String,
        onResult: (List<ReservationModel>) -> Unit
    ) {
        return try {
            val querySnapshot = db.collection("reservations").whereEqualTo("passengerId", passengerId).get().await()
            val reservations = querySnapshot.documents.mapNotNull { document ->
                document.toObject(ReservationModel::class.java)
            }
            onResult(reservations)
        } catch (e: Exception) {
            onResult(emptyList())
        }
    }
}