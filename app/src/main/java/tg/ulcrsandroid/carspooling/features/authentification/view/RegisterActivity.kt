/*package tg.ulcrsandroid.carspooling.features.authentification.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import tg.ulcrsandroid.carspooling.R
import tg.ulcrsandroid.carspooling.core.utils.ValidationUtils
import tg.ulcrsandroid.carspooling.data.repository.user.FirebaseUserRepository
import tg.ulcrsandroid.carspooling.domain.usecases.LoginUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.RegisterUserCase
import tg.ulcrsandroid.carspooling.domain.usecases.SignInWithGoogleUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.SignOutUseCase
import tg.ulcrsandroid.carspooling.features.authentification.viewmodel.AuthViewModel
import tg.ulcrsandroid.carspooling.features.authentification.viewmodel.AuthViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private lateinit var authViewModel: AuthViewModel
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialisation des vues avec findViewById
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginButton = findViewById(R.id.loginButton)

        val userRepository = FirebaseUserRepository()
        val registerUseCase = RegisterUserCase(userRepository)
        val signInWithGoogleUseCase = SignInWithGoogleUseCase(userRepository)
        val signOutUseCase = SignOutUseCase(userRepository)
        val loginUseCase = LoginUseCase(userRepository)
        val factory = AuthViewModelFactory(signInWithGoogleUseCase,registerUseCase,loginUseCase,signOutUseCase)
        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        setupObservers()

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (!ValidationUtils.isValidEmail(email)) {
                emailEditText.error = "Invalid email"
                return@setOnClickListener
            }

            if (!ValidationUtils.isValidPassword(password)) {
                passwordEditText.error = "Password must be at least 6 characters"
                return@setOnClickListener
            }

            authViewModel.register(email,password)
        }

        findViewById<View>(R.id.buttonSignOut2).setOnClickListener{
            authViewModel.signOut()
        }

    }

    private fun setupObservers() {
        authViewModel.user.observe(this) { user ->
            user?.let {
                Log.i("Register", "Logged in: ${it.uid}")
                Log.i("Register", "Logged in: ${it.email}")

                // Naviguer vers la page principale
            }
        }

        authViewModel.error.observe(this) { error ->
            error?.let {
                Log.e("Register", "Login failed: $it")
            }
        }
    }

}*/