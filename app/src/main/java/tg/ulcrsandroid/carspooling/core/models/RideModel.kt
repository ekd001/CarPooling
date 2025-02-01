package tg.ulcrsandroid.carspooling.core.models

import java.util.UUID

data class RideModel(
    val rideId: String = UUID.randomUUID().toString(),
    val driverId: String = "",
    val departure: String? = null,
    val arrival: String? = null,
    val dateRide: String? = null,
    val hoursRide:String? = null,
    val price: Double? = null,
    val placeNumber: Int? = null,
    val reservationList: List<String>? = null
)

