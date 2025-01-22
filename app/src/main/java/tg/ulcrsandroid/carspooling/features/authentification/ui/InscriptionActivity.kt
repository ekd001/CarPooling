package tg.ulcrsandroid.carspooling.features.authentification.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import tg.ulcrsandroid.carspooling.MainActivity
import tg.ulcrsandroid.carspooling.R
import tg.ulcrsandroid.carspooling.data.repository.FirebaseUserRepository
import tg.ulcrsandroid.carspooling.domain.usecases.authentification.LoginUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.authentification.RegisterUserCase
import tg.ulcrsandroid.carspooling.domain.usecases.authentification.SignInWithGoogleUseCase
import tg.ulcrsandroid.carspooling.domain.usecases.authentification.SignOutUseCase
import tg.ulcrsandroid.carspooling.features.authentification.viewmodel.AuthViewModel
import tg.ulcrsandroid.carspooling.features.authentification.viewmodel.AuthViewModelFactory

class InscriptionActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var roleRadioGroup: RadioGroup
    private lateinit var radioPassengerButton: RadioButton
    private lateinit var signupButton: Button
    private lateinit var googleSignUpButton: LinearLayout
    private lateinit var alreadyAccountTextView: TextView
    private lateinit var driverLicenseEditText: EditText
    private lateinit var usernameEditText: EditText
    private lateinit var authViewModel: AuthViewModel
    private lateinit var googleSignInClient: GoogleSignInClient

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
                    Log.i("Auth", "Google Sign-In successful")
                }
            } catch (e: Exception) {
                Log.i("Auth", "Google Sign-In failed", e)
            }
        } else {
            Log.i("Auth", "Google Sign-In canceled or failed")
        }
    }


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscription)

        val userRepository = FirebaseUserRepository()
        val loginUseCase = LoginUseCase(userRepository)
        val registerUserCase = RegisterUserCase(userRepository)
        val signInWithGoogleUseCase = SignInWithGoogleUseCase(userRepository)
        val signOutUseCase = SignOutUseCase(userRepository)
        val factory = AuthViewModelFactory(signInWithGoogleUseCase, registerUserCase, loginUseCase,signOutUseCase)
        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]
        setupGoogleSignIn()
        //authViewModel.login("real@madrid.com","real1234")

        emailEditText = findViewById(R.id.email_field)
        passwordEditText = findViewById(R.id.password_field)
        confirmPasswordEditText = findViewById(R.id.confirm_password_field)
        roleRadioGroup = findViewById(R.id.rgRole)
        radioPassengerButton = findViewById(R.id.rbPassenger)
        radioPassengerButton.isChecked = true
        signupButton = findViewById(R.id.signup_button)
        googleSignUpButton = findViewById(R.id.btnGoogleSignUp)
        alreadyAccountTextView = findViewById(R.id.tvAlreadyAccount)
        driverLicenseEditText = findViewById(R.id.etDriverLicense)
        usernameEditText = findViewById(R.id.username_field)

        driverLicenseEditText.isEnabled = false
        roleRadioGroup.setOnCheckedChangeListener{_, checkedId ->
            when (checkedId) {
                R.id.rbPassenger -> {
                    driverLicenseEditText.visibility = View.GONE
                }
                R.id.rbDriver -> {
                    driverLicenseEditText.visibility = View.VISIBLE
                }
            }
        }

        alreadyAccountTextView.setOnClickListener {
            finish()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        signupButton.isEnabled=false
        signupButton.setBackgroundColor(Color.DKGRAY)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateInputs()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        usernameEditText.addTextChangedListener(textWatcher)
        emailEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)
        confirmPasswordEditText.addTextChangedListener(textWatcher)

        signupButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            if (password == confirmPassword) {
                authViewModel.register(email, password, username)
                finish()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show()
            }
        }

        googleSignUpButton.setOnClickListener {
            signInWithGoogle()
            Log.i("Auth", "Google Sign-In button clicked")
            // un dialog pour s'inscrire avec google
        }
    }

    private fun validateInputs() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()

        if (isValidEmail(email) && password.isNotEmpty() &&
            confirmPassword.isNotEmpty() &&
            password == confirmPassword) {
            signupButton.isEnabled = true
            signupButton.setBackgroundColor(Color.parseColor("#1e272e"))
        } else {
            signupButton.isEnabled = false
            signupButton.setBackgroundColor(Color.GRAY)
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id)) // Placez votre ID client ici
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent) // Utilisez le lanceur ici
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}