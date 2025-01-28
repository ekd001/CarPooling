package tg.ulcrsandroid.carspooling.core.models

import tg.ulcrsandroid.carspooling.core.utils.ReservationStatus

data class ReservationModel (
    val reservationId: String = "",
    val date:String? = null,
    val status:String? = null,
    val passengerId:String = "",
    val rideId:String = "",
)