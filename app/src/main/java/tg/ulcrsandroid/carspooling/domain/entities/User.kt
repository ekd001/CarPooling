package tg.ulcrsandroid.carspooling.domain.entities

open class User(
    var id: String,
    val displayName: String?,
    val email: String?,
    val carLicenseNumber: String?,
    private var connected:Boolean = false
){
    fun isConnected():Boolean{
        return connected
    }

    fun setConnected(connected: Boolean){
        this.connected = connected
    }

    fun isDriver():Boolean{
        return carLicenseNumber != null
    }
}