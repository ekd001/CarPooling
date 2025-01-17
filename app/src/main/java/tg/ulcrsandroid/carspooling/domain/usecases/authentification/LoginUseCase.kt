package tg.ulcrsandroid.carspooling.domain.usecases.authentification

import tg.ulcrsandroid.carspooling.core.models.UserModel
import tg.ulcrsandroid.carspooling.data.repository.UserRepository

class LoginUseCase(private val userRepository: UserRepository) {
    suspend fun execute(email:String, password:String, onResult:(UserModel?, String?,String?)->Unit){
        userRepository.login(email, password,onResult)
    }
}