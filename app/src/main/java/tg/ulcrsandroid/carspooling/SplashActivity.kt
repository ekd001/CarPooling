package tg.ulcrsandroid.carspooling

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import tg.ulcrsandroid.carspooling.features.authentification.ui.LoginActivity
import tg.ulcrsandroid.carspooling.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var ui: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(ui.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}