package tg.ulcrsandroid.carspooling.core.utils

import android.util.Log
import tg.ulcrsandroid.carspooling.core.models.UserModel

object GlobalUser {
    private var _user: UserModel? = null
    val user: UserModel?
        get() = _user

    fun setUser(user: UserModel) {
        _user = user
    }

    fun displayUser(){
        if (_user != null) {
            Log.i(Constants.TAG_AUTH, "User global : ${_user!!.displayName}")
        } else {
            Log.i(Constants.TAG_AUTH, "User global : indisponible")
        }
    }
}