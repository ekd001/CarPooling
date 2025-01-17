package tg.ulcrsandroid.carspooling.domain.entities

class User(
    val id: String,
    val name: String,
    val email: String,
    val connected:Boolean = false
){
    fun isConnected():Boolean{
        return connected
    }
}