package tg.ulcrsandroid.carspooling.features.authentification.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import tg.ulcrsandroid.carspooling.core.models.UserModel
import tg.ulcrsandroid.carspooling.core.utils.AuthManager
import tg.ulcrsandroid.carspooling.core.utils.Constants
import tg.ulcrsandroid.carspooling.domain.usecases.authentification.LoginUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.authentification.RegisterUserCase
import tg.ulcrsandroid.carspooling.domain.usecases.authentification.SignInWithGoogleUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.authentification.SignOutUseCase

class AuthViewModel(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val registerUserCase: RegisterUserCase,
    private val loginUseCase: LoginUseCase,
    private val signOutUseCase: SignOutUseCase
):ViewModel(){
    val user = MutableLiveData<UserModel?>()
    val error = MutableLiveData<String?>()
    private val _idToken = MutableLiveData<String?>()
    val idToken: LiveData<String?> = _idToken

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            try {
                user.value = signInWithGoogleUseCase(idToken)
                _idToken.value = idToken
                AuthManager.setToken(idToken)
                AuthManager.displayToken()
            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            try {
                // Appel du cas d'utilisation pour l'enregistrement
                registerUserCase.execute(email, password) { firebaseUser, idToken, errorMessage ->
                    if (firebaseUser != null && idToken != null) {
                        // Si l'enregistrement réussit, on met à jour l'utilisateur et le token
                        user.value = UserModel(uid = firebaseUser.uid,email=firebaseUser.email, displayName = null)
                        _idToken.value = idToken
                        AuthManager.setToken(idToken)
                        AuthManager.displayToken()
                    } else {
                        // Si une erreur se produit, on met à jour l'erreur
                        error.value = errorMessage
                    }
                }
            } catch (e: Exception) {
                // Gestion des erreurs dans le cas où le cas d'utilisation échoue
                error.value = e.message
            }
        }
    }

    fun login(email: String, password: String){
        viewModelScope.launch {
            try{
                loginUseCase.execute(email, password) { loggedInUser, idToken,errorMessage ->
                    // Vérification de la réponse du login
                    if (loggedInUser != null && idToken != null) {
                        // Connexion réussie, on met à jour l'utilisateur
                        user.value = loggedInUser
                        _idToken.value = idToken
                        AuthManager.setToken(idToken)
                        val token = AuthManager.idToken
                        if (token != null) {
                            Log.i(Constants.TAG_AUTH, "User token global : ${token}")
                        } else {
                            Log.i(Constants.TAG_AUTH, "User token global : indisponible")
                        }
                    } else {
                        // En cas d'erreur, on met à jour l'erreur
                        error.value = errorMessage
                    }
                }
            }catch (e:Exception){

            }
        }
    }

    fun signOut(){
        viewModelScope.launch {
            try {
                signOutUseCase.execute()
                AuthManager.clearToken()
                Log.i(Constants.TAG_AUTH, "Sign Out")
                Log.i(Constants.TAG_AUTH, "Tokken global : ${AuthManager.idToken}")
            }catch (e:Exception){
                error.value = e.message
            }
        }
    }

}