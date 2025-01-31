package tg.ulcrsandroid.carspooling

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import tg.ulcrsandroid.carspooling.core.utils.Constants
import tg.ulcrsandroid.carspooling.databinding.FragmentProfilBinding
import kotlin.properties.Delegates

class ProfilFragment : Fragment() {

    private var isPassengerProfile = true
    private lateinit var ui: FragmentProfilBinding
    private lateinit var usernameText:TextView
    private lateinit var emailText: TextView
    private lateinit var carLicenseNumberText: TextView

    private val sharedPreferences by lazy {
        requireContext().getSharedPreferences("ProfilFragment", AppCompatActivity.MODE_PRIVATE)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profil_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    // Gérer les clics sur les icônes de la Toolbar
    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                // Gérer la déconnexion
                Toast.makeText(requireContext(), "Déconnexion...", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profil, container, false)

        // Restaurer isPassengerProfile depuis SharedPreferences
        isPassengerProfile = getPassengerProfileState()

        // Référence à la Toolbar
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setTitleTextAppearance(requireContext(), R.style.CustomToolbarStyle)
        toolbar.title = "Profil"

        // Initialiser le reste des éléments
        val toggleProfileButton = view.findViewById<Button>(R.id.toggleProfileButton)
        toggleProfileButton.text = if (isPassengerProfile) {
            "Passer au profil conducteur"
        } else {
            "Passer au profil passager"
        }
        toggleProfileButton.setOnClickListener {
            if (isPassengerProfile) {
                toggleProfileButton.text = "Passer au profil conducteur"
                isPassengerProfile = !isPassengerProfile
                // Démarrer l'activité ConducteurActivity
                val intent = Intent(requireContext(), ConducteurActivity::class.java)
                startActivity(intent)
            } else {
                toggleProfileButton.text = "Passer au profil passager"
                isPassengerProfile = !isPassengerProfile
                // Démarrer l'activité ConducteurActivity
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }
            savePassengerProfileState(isPassengerProfile)
        }
        //afficher les informations sur le profil
        val username = arguments?.getString("username")
        val email = arguments?.getString("email")
        val carLicenseNumber = arguments?.getString("carLicenseNumber")

        Log.i("ProfilFragment", "Username: $username")
        Log.i("ProfilFragment", "Email: $email")
        Log.i("ProfilFragment", "Car License Number: $carLicenseNumber")

        ui = FragmentProfilBinding.inflate(layoutInflater)
        usernameText = view.findViewById(R.id.usernameText)
        emailText = view.findViewById(R.id.emailText)
        carLicenseNumberText = view.findViewById(R.id.permisText)
        usernameText.text = username
        emailText.text = email
        carLicenseNumberText.text = "carLicenseNumber"
        println(ui.permisText.text)
        // Activer le menu dans le fragment
        setHasOptionsMenu(true)

        return view
    }

    private fun getPassengerProfileState(): Boolean {
        return sharedPreferences.getBoolean("isPassengerProfile", true)
    }

    private fun savePassengerProfileState(isPassenger: Boolean) {
        sharedPreferences.edit().putBoolean("isPassengerProfile", isPassenger).apply()
    }

}