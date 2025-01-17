package tg.ulcrsandroid.carspooling.domain.usecases.authentification

import com.google.firebase.auth.FirebaseUser
import tg.ulcrsandroid.carspooling.data.repository.UserRepository

class RegisterUserCase(private val userRepository: UserRepository){
    suspend fun execute(email:String, password:String, onResult: (FirebaseUser?, String?, String?)->Unit){
        userRepository.register(email, password,onResult)
    }
}