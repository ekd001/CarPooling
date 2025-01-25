package tg.ulcrsandroid.carspooling.domain.entities

class Ride (
    val rideId: String = "",
    val departure: String? = null,
    val arrival: String? = null,
    val dateRide: String? = null,
    val price: Double? = null,
    val driver: Driver? = null
)