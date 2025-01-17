package tg.ulcrsandroid.carpooling.faetures.authentication.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import tg.ulcrsandroid.carpooling.MainActivity
import tg.ulcrsandroid.carpooling.R

class InscriptionActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var roleRadioGroup: RadioGroup
    private lateinit var radioPassengerButton: RadioButton
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
        roleRadioGroup = findViewById(R.id.rgRole)
        radioPassengerButton = findViewById(R.id.rbPassenger)
        radioPassengerButton.isChecked = true
        signupButton = findViewById(R.id.signup_button)
        googleSignUpButton = findViewById(R.id.btnGoogleSignUp)
        alreadyAccountTextView = findViewById(R.id.tvAlreadyAccount)
        driverLicenseEditText = findViewById(R.id.etDriverLicense)
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

        emailEditText.addTextChangedListener(textWatcher)
        passwordEditText.addTextChangedListener(textWatcher)
        confirmPasswordEditText.addTextChangedListener(textWatcher)

        signupButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()
            if (password == confirmPassword) {
                TODO("Ajouter l'inscription")
                finish()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }else {
                Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        googleSignUpButton.setOnClickListener {
            finish()
            TODO("Ajouter l'inscription avec Google(firebase)")
            // un dialog pour s'inscrire avec google



        }


    }

    private fun validateInputs() {
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val confirmPassword = confirmPasswordEditText.text.toString()

        if (isValidEmail(email)
            && password.isNotEmpty()
            && confirmPassword.isNotEmpty()
            && password == confirmPassword) {
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