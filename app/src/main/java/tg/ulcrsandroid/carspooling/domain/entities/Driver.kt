package tg.ulcrsandroid.carspooling.domain.entities

class Driver (
    id: String,
    displayName: String?,
    email: String?,
    carLicenseNumber: String,
    connected: Boolean = false,
) : User(id, displayName, email, carLicenseNumber, connected)