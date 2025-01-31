package tg.ulcrsandroid.carpooling.faetures.authentication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tg.ulcrsandroid.carpooling.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var ui: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(ui.root)
    }
}