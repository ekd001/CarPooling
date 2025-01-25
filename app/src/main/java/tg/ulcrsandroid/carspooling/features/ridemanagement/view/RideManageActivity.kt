package tg.ulcrsandroid.carspooling.features.ridemanagement.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import tg.ulcrsandroid.carspooling.CarSpoolingApplication
import tg.ulcrsandroid.carspooling.core.models.RideModel
import tg.ulcrsandroid.carspooling.features.ridemanagement.viewModel.RideMgmtViewModel

class RideManageActivity: AppCompatActivity() {
    private lateinit var rideMgmtViewModel: RideMgmtViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val application = application as CarSpoolingApplication
        rideMgmtViewModel = ViewModelProvider(this, application.factoryRideManagement)[RideMgmtViewModel::class.java]

        val rideExample = RideModel(
            rideId = "R123458",               // ID unique du trajet
            driverId = "D987654",             // ID du conducteur
            departure = "Lomé",               // Ville de départ
            arrival = "Kpalimé",              // Ville d'arrivée
            dateRide = "2025-02-01",          // Date du trajet
            price = 5000.0,                   // Prix du trajet en FCFA
            placeNumber = 4,                  // Nombre de places disponibles
            reservationList = listOf("P1", "P2", "P3") // Liste des ID des réservations
        )

        rideMgmtViewModel.deleteRide("R123458")

    }
}