package tg.ulcrsandroid.carpooling

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import tg.ulcrsandroid.carpooling.databinding.FragmentDemandeDetailsBinding

class DemandeDetailsFragment : Fragment() {

    private var _ui: FragmentDemandeDetailsBinding? = null
    private val ui get() = _ui!!

    private lateinit var demande: DemandesTrajets
    private var position: Int = -1
    private lateinit var adapter: DemandesTrajetsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _ui = FragmentDemandeDetailsBinding.inflate(inflater, container, false)
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setTitleTextAppearance(requireContext(), R.style.CustomToolbarStyle)
        toolbar.title = "Demandes de reservations"

        // Récupérer les arguments passés au fragment
        demande = arguments?.getParcelable("demandeTrajet") ?: throw IllegalStateException("Demande manquante")
        position = arguments?.getInt("position") ?: -1

        // Afficher les détails de la demande
        ui.passager.text = demande.passager
        ui.depart.text = demande.depart
        ui.destination.text = demande.destination
        ui.distance.text = "${demande.distance}km"

        val adapter = (requireActivity() as ConducteurActivity).getAdapter()

        // Gérer le clic sur le bouton "Refuser"
        ui.buttonRefuser.setOnClickListener {
            // Action pour refuser la demande
            demande.etat = -1
            adapter.updateItem(position, -1)
            println("${demande.passager} ${demande.etat}")
            Toast.makeText(requireContext(), "Demande refusée", Toast.LENGTH_SHORT).show()
            requireActivity().onBackPressed()
        }

        // Gérer le clic sur le bouton "Valider"
        ui.buttonValider.setOnClickListener {
            // Action pour valider la demande
            demande.etat = 1
            adapter.updateItem(position, 1)
            println("${demande.passager} ${demande.etat}")
            Toast.makeText(requireContext(), "Demande validée", Toast.LENGTH_SHORT).show()
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _ui = null
    }
}