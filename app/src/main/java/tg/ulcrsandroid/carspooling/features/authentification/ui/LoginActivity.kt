package tg.ulcrsandroid.carspooling.features.authentification.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import tg.ulcrsandroid.carpooling.databinding.ActivityLoginBinding
import tg.ulcrsandroid.carpooling.faetures.authentication.ui.InscriptionActivity
import tg.ulcrsandroid.carspooling.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var ui: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(ui.root)

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
//            if (email.isEmpty() || password.isEmpty()) {
//                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show()
//            } else {
//
//                Toast.makeText(this, "Connexion réussie", Toast.LENGTH_SHORT).show()
//            }

            TODO("Ce qu'il faut pour la connexion")

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
        TODO("Not yet implemented")
    }
}