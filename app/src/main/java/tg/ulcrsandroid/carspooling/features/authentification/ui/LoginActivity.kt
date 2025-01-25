package tg.ulcrsandroid.carspooling.features.authentification.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import tg.ulcrsandroid.carspooling.CarSpoolingApplication
import tg.ulcrsandroid.carspooling.MainActivity
import tg.ulcrsandroid.carspooling.databinding.ActivityLoginBinding
import tg.ulcrsandroid.carspooling.features.authentification.viewmodel.AuthViewModel
import tg.ulcrsandroid.carspooling.features.authentification.viewmodel.AuthViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var ui: ActivityLoginBinding
    private lateinit var factory: AuthViewModelFactory
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(ui.root)

        val application = application as CarSpoolingApplication
        factory = application.factoryAuthentifcation
        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        ui.loginButton.isEnabled = false
        ui.loginButton.setBackgroundColor(Color.DKGRAY)

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validateInputs()
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        ui.loginButton.isEnabled = false
        ui.emailInput.addTextChangedListener(textWatcher)
        ui.passwordInput.addTextChangedListener(textWatcher)
        ui.loginButton.setOnClickListener {
            val email = ui.emailInput.text.toString()
            val password = ui.passwordInput.text.toString()
           if (email.isEmpty() || password.isEmpty()) {
               Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
            } else {
                authViewModel.login(email, password,this)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        ui.registerText.setOnClickListener {
            finish()
            val intent = Intent(this, InscriptionActivity::class.java)
            startActivity(intent)
        }

    }

    private fun validateInputs() {
        val email = ui.emailInput.text.toString()
        val password = ui.passwordInput.text.toString()
        if (email.isEmpty() || password.isEmpty()) {
            ui.loginButton.isEnabled = false
            ui.loginButton.setBackgroundColor(Color.DKGRAY)
        } else {
            ui.loginButton.isEnabled = true
            ui.loginButton.setBackgroundColor(Color.BLUE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}

