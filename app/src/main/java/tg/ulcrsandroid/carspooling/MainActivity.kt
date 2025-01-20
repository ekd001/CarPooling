package tg.ulcrsandroid.carpooling

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import tg.ulcrsandroid.carpooling.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var ui: ActivityMainBinding
    private lateinit var googleMap: GoogleMap
    lateinit var searchIcon: ImageView
    lateinit var searchInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)

        // Initialisation de la carte
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        searchIcon = ui.searchIcon
        searchInput = ui.searchInput
        var isActiveIcon = false

        // Gestion du clic sur l'icône
        searchIcon.setOnClickListener {
            if (isActiveIcon) {
                // Revenir à l'icône par défaut
                searchIcon.setImageResource(R.drawable.google_maps_icon)
                isActiveIcon = false
                searchInput.clearFocus()
            }
        }

        // Détecter le focus sur l'EditText
        searchInput.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus && !isActiveIcon) {
                // Changer l'icône lorsque l'EditText est sélectionné
                searchIcon.setImageResource(R.drawable.back_icon)
                isActiveIcon = true
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Ajoutez un marqueur sur une position par défaut
        val defaultLocation = LatLng(48.8566, 2.3522) // Paris
        googleMap.addMarker(MarkerOptions().position(defaultLocation).title("Marker in Paris"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultLocation, 10f))
    }
}