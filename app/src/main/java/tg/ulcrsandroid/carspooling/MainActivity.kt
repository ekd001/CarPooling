package tg.ulcrsandroid.carspooling

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tg.ulcrsandroid.carspooling.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var ui: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)
    }
}