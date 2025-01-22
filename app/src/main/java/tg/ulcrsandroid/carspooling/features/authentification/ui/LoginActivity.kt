package tg.ulcrsandroid.carspooling.features.authentification.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tg.ulcrsandroid.carspooling.core.utils.GlobalUser
import tg.ulcrsandroid.carspooling.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var ui: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(ui.root)

        ui.registerText.setOnClickListener {
            val intent = Intent(this, InscriptionActivity::class.java)
            startActivity(intent)
        }

        GlobalUser.displayUser()
    }
}