package tg.ulcrsandroid.carspooling.domain.usecases.authentification

import tg.ulcrsandroid.carspooling.core.models.UserModel
import tg.ulcrsandroid.carspooling.data.repository.UserRepository

class SignInWithGoogleUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(idToken: String): UserModel? {
        return repository.signInWithGoogle(idToken)
    }
}