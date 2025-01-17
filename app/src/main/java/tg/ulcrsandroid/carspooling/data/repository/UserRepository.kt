package tg.ulcrsandroid.carspooling.data.repository

import com.google.firebase.auth.FirebaseUser
import tg.ulcrsandroid.carspooling.core.models.UserModel
import tg.ulcrsandroid.carspooling.domain.entities.User

/**
 * Interface contient les méthodes utilisés pour designer
 */
interface UserRepository {
    suspend fun getCurrentUser(): UserModel?
    suspend fun signInWithGoogle(idToken: String): UserModel?
    suspend fun register(email:String, password:String,username:String,onResult:(FirebaseUser?, String?,String?) -> Unit)
    suspend fun login(email:String, password: String, onResult: (UserModel?, String?,String?) -> Unit)
    suspend fun signOut()
}