package tg.ulcrsandroid.carpooling

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.Manifest
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.location.LocationManagerCompat.getCurrentLocation
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import tg.ulcrsandroid.carpooling.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    lateinit var ui: ActivityMainBinding
    private lateinit var googleMap: GoogleMap
    private lateinit var mapFragment: Fragment
    lateinit var searchIcon: ImageView
    lateinit var searchInput: AutoCompleteTextView
    lateinit var bottomNavigation: BottomNavigationView
    lateinit var suggestionsRecyclerView: RecyclerView
    private lateinit var routeDetailsLayout: View
    private lateinit var backIconRoute: ImageView
    private lateinit var startLocationText: TextView
    private lateinit var destinationText: TextView
    private lateinit var mapFragmentContainer: View
    private lateinit var bottomSheetView: View
    private lateinit var fragmentContainer: View
    private lateinit var homeContainer: View
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private val initiallyVisibleViews = mutableSetOf<View>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var isRouteBack = 0

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        Places.initialize(applicationContext, "AIzaSyAOVYRIgupAurZup5y1PRh8Ismb1A3lLao")
        val placesClient = Places.createClient(this)

        // Configurer les couleurs de la barre de statut/navigation
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                )
        window.statusBarColor = Color.TRANSPARENT


        // Liaison avec les éléments de l'UI
        bottomNavigation = ui.bottomNavigation
        suggestionsRecyclerView = ui.suggestionsRecyclerView
        searchIcon = ui.searchIcon
        searchInput = ui.searchInput
        backIconRoute = ui.backIconRoute
        routeDetailsLayout = ui.routeDetailsLayout
        startLocationText = ui.startLocationText
        destinationText = ui.destinationText
        mapFragmentContainer = ui.mapFragmentContainer
        fragmentContainer = ui.fragmentContainer
        homeContainer = ui.homeContainer
        bottomSheetView = ui.bottomSheet

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    window.decorView.systemUiVisibility = (
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                            )
                    window.statusBarColor = Color.TRANSPARENT
                    homeContainer.visibility = View.VISIBLE
                    fragmentContainer.visibility = View.GONE
                    supportFragmentManager.popBackStack()
                    true
                }
                R.id.nav_reserve -> {
                    //showFragment(ReservationFragment())
                    true
                }
                R.id.nav_profile -> {
                    window.decorView.systemUiVisibility = (
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                            )
                    window.statusBarColor = ContextCompat.getColor(this, R.color.mainColor)
                    homeContainer.visibility = View.GONE
                    fragmentContainer.visibility = View.VISIBLE
                    openFragment(ProfilFragment())
                    true
                }
                else -> false
            }
        }

        // Initialisation de la carte
        mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        (mapFragment as SupportMapFragment).getMapAsync(this)
        val mapLayoutContainerParams = mapFragmentContainer.layoutParams as ViewGroup.MarginLayoutParams

        var isActiveIcon = false

        // Gestion du clic sur l'icône
        searchIcon.setOnClickListener {
            if (isActiveIcon) {
                suggestionsRecyclerView.visibility = View.GONE
                mapFragmentContainer.visibility = View.VISIBLE
                // Revenir à l'état initial
                if (isRouteBack == 0) {
                    searchIcon.setImageResource(R.drawable.google_maps_icon)
                    isActiveIcon = false
                    searchInput.clearFocus()
                    searchInput.setText("")
                    mapFragmentContainer.layoutParams = mapLayoutContainerParams
                    bottomNavigation.visibility = View.VISIBLE
                } else {
                    routeDetailsLayout.visibility = View.VISIBLE
                    bottomSheetView.visibility = View.VISIBLE
                }
            }
        }

        /*// Réagir à la saisie dans la barre de recherche
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (query.isNotEmpty()) {
                    fetchSuggestions(query) { suggestions ->
                        updateSuggestionsRecyclerView(suggestions)
                    }
                } else {
                    // Si la barre de recherche est vide, vider les suggestions
                    updateSuggestionsRecyclerView(emptyList())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })*/

        // Détecter le focus sur l'EditText
        searchInput.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus && !isActiveIcon) {
                // Changer l'icône lorsque l'EditText est sélectionné
                searchIcon.setImageResource(R.drawable.back_icon)
                isActiveIcon = true
                ui.searchField.visibility = View.GONE
                showRouteDetailsLayout("")
            } else {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        val adapterDepart = ArrayAdapter(this, R.layout.item_suggestion_recherche, lieux)
        searchInput.setAdapter(adapterDepart)
        searchInput.setDropDownVerticalOffset(30)
        searchInput.threshold = 1
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Filtre les suggestions en fonction de l'entrée
                val filteredSuggestions = lieux.filter { it.startsWith(s.toString(), ignoreCase = true) }
                val filteredAdapter = ArrayAdapter(searchInput.context, R.layout.item_suggestion_recherche, filteredSuggestions)
                searchInput.setAdapter(filteredAdapter)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        searchInput.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            searchInput.setText(selectedItem)
            ui.searchField.visibility = View.GONE
            bottomSheetView.visibility = View.VISIBLE
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            showRouteDetailsLayout(selectedItem)
        }


        // Gestion du clic sur l'icône retour dans le conteneur des détails
        backIconRoute.setOnClickListener {
            routeDetailsLayout.visibility = View.GONE
            bottomSheetView.visibility = View.GONE
            suggestionsRecyclerView.visibility = View.VISIBLE
            ui.searchField.visibility = View.VISIBLE
            mapFragmentContainer.visibility = View.GONE
            isRouteBack = 0
        }

        // Clic sur les champs le champ de depart
        startLocationText.setOnClickListener {
            routeDetailsLayout.visibility = View.GONE
            bottomSheetView.visibility = View.GONE
            mapFragmentContainer.visibility = View.GONE
            ui.searchField.visibility = View.VISIBLE
            searchInput.setText(startLocationText.text)
            searchInput.setHint("Entrez le lieu de depart")
            isRouteBack = 1
        }
        // Clic sur les champs le champ de depart
        destinationText.setOnClickListener {
            routeDetailsLayout.visibility = View.GONE
            bottomSheetView.visibility = View.GONE
            suggestionsRecyclerView.visibility = View.VISIBLE
            mapFragmentContainer.visibility = View.GONE
            ui.searchField.visibility = View.VISIBLE
            searchInput.setHint("Entrez votre destination")
            searchInput.setText(destinationText.text)
            isRouteBack = 2
        }

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetView)

        // Définit les positions du BottomSheet
        bottomSheetBehavior.peekHeight = 400 // Position en bas
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        // Gestion des événements de glissement
        val screenHeight = resources.displayMetrics.heightPixels
        bottomSheetBehavior.halfExpandedRatio = 0.6f
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                //routeDetailsLayout.alpha = 1f
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        ui.bottomSheetIcon.visibility = View.VISIBLE
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        // La vue est arrêtée au milieu
                        ui.bottomSheetIcon.visibility = View.VISIBLE
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        ui.bottomSheetIcon.visibility = View.GONE
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // Vérifie si le relâchement est proche du milieu
                if (slideOffset > 0.3f && slideOffset < 0.75f) {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
                    //routeDetailsLayout.alpha = 1 - slideOffset
                }
            }
        })

        // Configurer RecyclerView
        val trajets = getDummyData()
        // Instanciez l'adaptateur avec les données
        val TrajetTrouvesadapter = TrajetsTrouvesAdapter(trajets)
        ui.trajetsTrouvesRecycler.layoutManager = LinearLayoutManager(this)
        ui.trajetsTrouvesRecycler.adapter = TrajetTrouvesadapter

    }

    private fun showRouteDetailsLayout(suggestion: String) {
        // Afficher la carte et le conteneur des détails
        routeDetailsLayout.visibility = View.VISIBLE
        mapFragmentContainer.visibility = View.VISIBLE

        // Cacher les autres éléments
        suggestionsRecyclerView.visibility = View.GONE
        bottomNavigation.visibility = View.GONE

        // Définir les informations des détails
        if (isRouteBack == 0) {
            startLocationText.text = ""
            destinationText.text = "$suggestion"
        } else if (isRouteBack == 1) {
            startLocationText.text = "$suggestion"
        } else {
            destinationText.text = "$suggestion"
        }
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            getCurrentLocation()
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val currentLocation = LatLng(location.latitude, location.longitude)
                    updateMapWithLocation(currentLocation)
                } else {
                    // Gestion de l'erreur si la localisation est nulle
                    val defaultLocation = LatLng(48.8566, 2.3522) // Paris
                    updateMapWithLocation(defaultLocation)
                }
            }
        }
    }

    private fun updateMapWithLocation(location: LatLng) {
        googleMap.addMarker(MarkerOptions().position(location).title("Current Location"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        checkLocationPermission()

        googleMap.setOnMapClickListener {
            toggleVisibility()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                // Permissions refusées, utilisez une position par défaut
                val defaultLocation = LatLng(48.8566, 2.3522) // Paris
                updateMapWithLocation(defaultLocation)
            }
        }
    }

    private fun toggleVisibility() {
        val views = listOf(
            ui.searchField,
            bottomSheetView,
            routeDetailsLayout,
            bottomNavigation,
            bottomSheetView
        )

        // Si c'est le premier clic, on enregistre les vues visibles
        if (initiallyVisibleViews.isEmpty()) {
            views.forEach {
                if (it.visibility == View.VISIBLE) {
                    initiallyVisibleViews.add(it) // Enregistrer les vues visibles
                }
            }
        }

        // Si une vue est visible, on les rend toutes invisibles
        if (views.any { it.visibility == View.VISIBLE }) {
            views.forEach { it.visibility = View.GONE }
        } else {
            // Sinon, on les rend visibles uniquement si elles étaient visibles avant le premier clic
            views.forEach {
                if (initiallyVisibleViews.contains(it)) {
                    it.visibility = View.VISIBLE
                }
            }
        }
    }

    fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }

    private fun getDummyData(): List<Trajet> {
        return listOf(
            Trajet(1, "Paris", "Lyon"),
            Trajet(2, "Marseille", "Nice"),
            Trajet(3, "Bordeaux", "Toulouse")
        )
    }

    fun fetchSuggestions(query: String, callback: (List<String>) -> Unit) {
        val placesClient = Places.createClient(this)
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .setCountry("FR") // Vous pouvez limiter les résultats à un pays spécifique
            .build()

        placesClient.findAutocompletePredictions(request)
            .addOnSuccessListener { response ->
                val suggestions = response.autocompletePredictions.map { it.getPrimaryText(null).toString() }
                callback(suggestions) // Retourner les suggestions via le callback
            }
            .addOnFailureListener { exception ->
                Log.e("Places", "Erreur lors de la récupération des suggestions : ${exception.message}")
                callback(emptyList()) // En cas d'échec, retourner une liste vide
            }
    }

    fun updateSuggestionsRecyclerView(suggestions: List<String>) {
        val adapter = SuggestionsAdapter(suggestions) { suggestion ->
            // Lorsqu'une suggestion est sélectionnée
            searchInput.setText(suggestion)
            ui.searchField.visibility = View.GONE
            bottomSheetView.visibility = View.VISIBLE
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            showRouteDetailsLayout(suggestion)
        }
        suggestionsRecyclerView.adapter = adapter
        suggestionsRecyclerView.visibility = if (suggestions.isNotEmpty()) View.VISIBLE else View.GONE
    }

    // Méthode pour ouvrir un fragment
    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null) // Ajouter à la pile arrière pour pouvoir revenir
            .commit()
    }
}