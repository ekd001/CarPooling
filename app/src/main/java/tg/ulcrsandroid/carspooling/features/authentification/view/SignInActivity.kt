/*package tg.ulcrsandroid.carspooling.features.authentification.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import tg.ulcrsandroid.carspooling.features.authentification.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import tg.ulcrsandroid.carspooling.R
import tg.ulcrsandroid.carspooling.core.utils.AuthManager
import tg.ulcrsandroid.carspooling.data.repository.FirebaseUserRepository
import tg.ulcrsandroid.carspooling.domain.usecases.LoginUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.RegisterUserCase
import tg.ulcrsandroid.carspooling.domain.usecases.SignInWithGoogleUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.SignOutUseCase
import tg.ulcrsandroid.carspooling.features.authentification.viewmodel.AuthViewModelFactory

class SignInActivity : AppCompatActivity() {
    private lateinit var authViewModel: AuthViewModel
    private lateinit var googleSignInClient: GoogleSignInClient

    // Utilisez ActivityResultLauncher pour gérer les résultats de Google Sign-In
    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data: Intent? = result.data
        if (result.resultCode == RESULT_OK && data != null) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account: GoogleSignInAccount? = task.getResult(Exception::class.java)
                account?.idToken?.let { idToken ->
                    authViewModel.signInWithGoogle(idToken)
                }
            } catch (e: Exception) {
                Log.i("Auth", "Google Sign-In failed", e)
            }
        } else {
            Log.i("Auth", "Google Sign-In canceled or failed")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Assurez-vous que vous avez un layout approprié pour cette activité.

        // Créez une instance de ViewModel avec la Factory
        val userRepository = FirebaseUserRepository()
        val signInWithGoogleUseCase = SignInWithGoogleUseCase(userRepository) // Assurez-vous d'obtenir cette instance correctement
        val registerUserCase = RegisterUserCase(userRepository)
        val signOutUseCase = SignOutUseCase(userRepository)
        val loginUseCase = LoginUseCase(userRepository)
        val factory = AuthViewModelFactory(signInWithGoogleUseCase, registerUserCase, loginUseCase,signOutUseCase)
        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
        setupGoogleSignIn()

        // Observe ViewModel
        authViewModel.user.observe(this) { user ->
            user?.let {
                Log.i("Auth", "User logged in: ${it.uid}")
                Log.i("Auth", "User logged in: ${it.email}")
                Log.i("Auth", "User logged in: ${it.displayName}")

            }


        }
        Log.i("Auth", "User tokken variable : ${authViewModel.idToken}")

        authViewModel.error.observe(this) { error ->
            error?.let {
                Log.i("Auth", "Error: $it")
            }
        }

        findViewById<View>(R.id.button).setOnClickListener {
            signInWithGoogle()

        }
        findViewById<View>(R.id.buttonToken).setOnClickListener{
            Log.i("Auth", "User tokken global activity : ${AuthManager.idToken}")

        }
        findViewById<View>(R.id.buttonSignOut).setOnClickListener{
            authViewModel.signOut()
        }
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.firebase_client_id)) // Placez votre ID client ici
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent) // Utilisez le lanceur ici
    }
}*/