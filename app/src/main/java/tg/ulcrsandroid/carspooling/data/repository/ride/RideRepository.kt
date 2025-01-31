package tg.ulcrsandroid.carspooling.data.repository.ride

import tg.ulcrsandroid.carspooling.core.models.ReservationModel
import tg.ulcrsandroid.carspooling.core.models.RideModel

interface RideRepository {
    suspend fun add(rideModel: RideModel, onResult: (Boolean) -> Unit)
    suspend fun delete(rideId: String, onResult: (Boolean) -> Unit)
    suspend fun search(departure: String, arrival: String,date:String,placeNumber:Int,onResult: (List<RideModel>) -> Unit)
    suspend fun reservation(reservation: ReservationModel, onResult: (Boolean) -> Unit)
    suspend fun update(reservationId: String, status: String, onResult: (Boolean) -> Unit)
}