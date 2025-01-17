package tg.ulcrsandroid.carspooling.core.utils

import android.util.Log

/**
 *Recuperer le token lors de l'authentification pour ensuite le recuperer n'importe où dans l'application
 * et supprimer le token lors de la fermeture de session
 *
 * @author EKLOU
 * @version 1.0
 */

object AuthManager {
    private var _idToken: String? = null
    val idToken: String?
        get() = _idToken

    fun setToken(token: String) {
        _idToken = token
    }


    fun clearToken() {
        _idToken = null
    }

    fun displayToken() {
        if (_idToken != null) {
            Log.i(Constants.TAG_AUTH, "User token global : ${_idToken}")
        } else {
            Log.i(Constants.TAG_AUTH, "User token global : indisponible")
        }
    }
}