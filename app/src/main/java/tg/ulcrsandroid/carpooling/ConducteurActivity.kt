package tg.ulcrsandroid.carpooling

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import tg.ulcrsandroid.carpooling.databinding.ActivityConducteurBinding
import tg.ulcrsandroid.carpooling.databinding.ActivityMainBinding

class ConducteurActivity : AppCompatActivity(), DemandesTrajetsFragment.OnAdapterSharedListener {

    lateinit var ui: ActivityConducteurBinding
    lateinit var bottomNavigation: BottomNavigationView
    private lateinit var fragmentContainer: View

    private lateinit var demandesTrajetsFragment: DemandesTrajetsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityConducteurBinding.inflate(layoutInflater)
        setContentView(ui.root)
        bottomNavigation = ui.bottomNavigation
        fragmentContainer = ui.fragmentContainer

        if (savedInstanceState == null) {
            val defaultFragment = DemandesTrajetsFragment()
            setTitle("Demandes de reservations")
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, defaultFragment)
                .commit()
        }

        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_demande -> {
                    fragmentContainer.visibility = View.VISIBLE
                    openFragment(DemandesTrajetsFragment())
                    true
                }
                R.id.nav_ajouter -> {
                    fragmentContainer.visibility = View.VISIBLE
                    openFragment(CreerTrajetFragment())
                    true
                }
                R.id.nav_trajets -> {
                    //showFragment(ReservationFragment())
                    true
                }
                R.id.nav_profile -> {
                    fragmentContainer.visibility = View.VISIBLE
                    openFragment(ProfilFragment())
                    true
                }
                else -> false
            }
        }

        demandesTrajetsFragment = DemandesTrajetsFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, demandesTrajetsFragment)
            .commit()

    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null) // Ajouter à la pile arrière pour pouvoir revenir
            .commit()
    }

    override fun getAdapter(): DemandesTrajetsAdapter {
        return demandesTrajetsFragment.provideAdapter() ?: throw IllegalStateException("Adapter is not initialized")
    }
}