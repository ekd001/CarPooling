package tg.ulcrsandroid.carspooling.domain.usecases.authentification

import tg.ulcrsandroid.carspooling.data.repository.UserRepository

class SignOutUseCase(private val userRepository: UserRepository) {
    suspend fun execute(){
        userRepository.signOut()
    }
}