package tg.ulcrsandroid.carspooling

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import tg.ulcrsandroid.carspooling.core.models.RideModel
import tg.ulcrsandroid.carspooling.core.utils.Constants
import tg.ulcrsandroid.carspooling.databinding.ActivityDriverBinding
import tg.ulcrsandroid.carspooling.features.ridemanagement.viewModel.RideMgmtViewModel

class DriverActivity: AppCompatActivity() {
    private lateinit var ui: ActivityDriverBinding


    private lateinit var rideMgmtViewModel: RideMgmtViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityDriverBinding.inflate(layoutInflater)
        setContentView(ui.root)

       /* val application = application as CarSpoolingApplication
        rideMgmtViewModel = ViewModelProvider(this, application.factoryRideManagement)[RideMgmtViewModel::class.java]

        val rideExample = RideModel(
            rideId = intent.getStringExtra(Constants.TAG_USER).toString() + "1",               // ID unique du trajet
            driverId = intent.getStringExtra(Constants.TAG_USER).toString(),             // ID du conducteur
            departure = "Lomé",               // Ville de départ
            arrival = "Kpalimé",              // Ville d'arrivée
            dateRide = "2025-02-01",          // Date du trajet
            price = 5000.0,                   // Prix du trajet en FCFA
            placeNumber = 4,                  // Nombre de places disponibles
            reservationList = null // Liste des ID des réservations
        )

        ui.buttonTrajet.setOnClickListener {
            rideMgmtViewModel.addRide(rideExample)
        }

        ui.buttonR.setOnClickListener {
            rideMgmtViewModel.updateStatus(intent.getStringExtra(Constants.TAG_USER).toString() + "1", "refused")
        }

        ui.buttonA.setOnClickListener {
            rideMgmtViewModel.updateStatus(intent.getStringExtra(Constants.TAG_USER).toString() + "1", "accepted")
        }*/

    }
}