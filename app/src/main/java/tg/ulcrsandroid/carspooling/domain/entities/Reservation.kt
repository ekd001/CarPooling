package tg.ulcrsandroid.carspooling.domain.entities

import tg.ulcrsandroid.carspooling.core.utils.ReservationStatus

class Reservation (
    val reservationId: String = "",
    val date:String? = null,
    val status:String = ReservationStatus.WAITING.toString(),
    val passenger: Passenger? = null,
    val ride: Ride? = null,
)