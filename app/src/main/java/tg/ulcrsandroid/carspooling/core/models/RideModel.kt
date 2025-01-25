package tg.ulcrsandroid.carspooling.core.models

data class RideModel(
    val rideId: String = "",
    val driverId: String = "",
    val departure: String? = null,
    val arrival: String? = null,
    val dateRide: String? = null,
    val price: Double? = null,
    val placeNumber: Int? = null,
    val reservationList: List<String>? = null
)

