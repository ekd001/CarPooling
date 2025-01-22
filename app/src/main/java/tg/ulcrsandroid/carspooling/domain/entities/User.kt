package tg.ulcrsandroid.carspooling.domain.entities

class User(
    var id: String,
    val name: String?,
    val email: String?,
    val carLicenseNumber: String?,
    val connected:Boolean = false
){
    fun isConnected():Boolean{
        return connected
    }

    fun isDriver():Boolean{
        return carLicenseNumber != null
    }
}