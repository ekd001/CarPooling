package tg.ulcrsandroid.carspooling.core.models

import tg.ulcrsandroid.carspooling.core.utils.ReservationStatus
import java.util.UUID

data class ReservationModel (
    val reservationId: String = UUID.randomUUID().toString(),
    val date:String? = null,
    val heure:String? = null,
    val rideHeure: String? = null,
    val status:String? = null,
    val passengerId:String = "",
    val rideId:String = "",
    val rideDeparture: String? = null,
    val rideArrival: String? = null,
    val rideDate: String? = null,
)