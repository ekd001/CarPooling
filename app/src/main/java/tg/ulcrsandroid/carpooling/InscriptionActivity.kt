package tg.ulcrsandroid.carpooling

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InscriptionActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signupButton: Button
    private lateinit var googleSignUpButton: Button
    private lateinit var alreadyAccountTextView: TextView
    private lateinit var driverLicenseEditText: EditText


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inscription)
        emailEditText = findViewById(R.id.email_field)
        passwordEditText = findViewById(R.id.password_field)
        confirmPasswordEditText = findViewById(R.id.confirm_password_field)
        signupButton = findViewById(R.id.signup_button)
        googleSignUpButton = findViewById(R.id.btnGoogleSignUp)
        alreadyAccountTextView = findViewById(R.id.tvAlreadyAccount)
        driverLicenseEditText = findViewById(R.id.etDriverLicense)

        signupButton.isEnabled=false
        signupButton.setBackgroundColor(Color.DKGRAY)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateInputs()
            }

            override fun afterTextChanged(s: Editable?) {}
        }

        emailEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)
        confirmPasswordEditText.addTextChangedListener(textWatcher)

        signupButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            if (password == confirmPassword) {

            }else {
                Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun validateInputs() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()

        if (isValidEmail(email) && password.isNotEmpty() && confirmPassword.isNotEmpty() && password == confirmPassword) {
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

    override fun onDestroy() {
        super.onDestroy()
    }
}