package tg.ulcrsandroid.carspooling.features.authentification.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import tg.ulcrsandroid.carspooling.domain.usecases.authentification.LoginUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.authentification.RegisterUserCase
import tg.ulcrsandroid.carspooling.domain.usecases.authentification.SignInWithGoogleUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.authentification.SignOutUseCase

class AuthViewModelFactory(
    private val signInWithGoogleUseCase: SignInWithGoogleUseCase,
    private val registerUserCase: RegisterUserCase,
    private val loginUseCase: LoginUseCase,
    private val signOutUseCase: SignOutUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            AuthViewModel(signInWithGoogleUseCase, registerUserCase,loginUseCase, signOutUseCase) as T
        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
