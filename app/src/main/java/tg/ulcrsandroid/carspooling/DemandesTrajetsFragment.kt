
package tg.ulcrsandroid.carspooling

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tg.ulcrsandroid.carspooling.databinding.FragmentDemandesTrajetsBinding

class DemandesTrajetsFragment : Fragment() {
    private var _ui: FragmentDemandesTrajetsBinding? = null
    private val ui get() = _ui!!
    private var adapter = DemandesTrajetsAdapter(
        getSampleData(),
        onItemClick = { _, _ -> },
        onDeleteClick = { _, _ -> }
    )

    lateinit var demandesTrajetsRecyclerView: RecyclerView

    private val demandesTrajets = getSampleData()


    // Interface pour partager l'adaptateur
    interface OnAdapterSharedListener {
        fun getAdapter(): DemandesTrajetsAdapter
    }

    private var adapterSharedListener: OnAdapterSharedListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnAdapterSharedListener) {
            adapterSharedListener = context
        } else {
            throw RuntimeException("$context must implement OnAdapterSharedListener")
        }
    }
    override fun onDetach() {
        super.onDetach()
        adapterSharedListener = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _ui = FragmentDemandesTrajetsBinding.inflate(inflater, container, false)
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurer la Toolbar
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setTitleTextAppearance(requireContext(), R.style.CustomToolbarStyle)


        // Configurer le RecyclerView
        ui.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Créer et définir l'adaptateur
        adapter = DemandesTrajetsAdapter(
            getSampleData(),
            onItemClick = { demandeTrajet, position ->
                // Action lorsqu'on clique sur l'élément
                val bundle = Bundle().apply {
                    putParcelable("demandeTrajet", demandeTrajet)
                    putInt("position", position)
                }
                val fragment = DemandeDetailsFragment().apply {
                    arguments = bundle
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit()
            },
            onDeleteClick = { demandeTrajet, position ->
                val bundle = Bundle().apply {
                    putParcelable("nomPassager", demandeTrajet)
                    putInt("position", position)
                }
                val fragment = ChatFragment().apply {
                    arguments = bundle
                }
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        )
        ui.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        ui.recyclerView.adapter = adapter

        adapterSharedListener?.getAdapter()?.let {}
    }

    private fun getSampleData(): MutableList<DemandesTrajets> {
        return mutableListOf(
            DemandesTrajets("KAMDE Steeve", "Agoe", "Segbe", 7.6),
            DemandesTrajets("EKLOU Gaston", "UL", "Adidogome", 5.3),
            DemandesTrajets("BASSOWOU Edouard", "Togble", "UL", 10.2),
            DemandesTrajets("ATUAKUMA Gabriel", "Adewi", "Zongo", 9.0)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _ui = null
    }

    fun provideAdapter(): DemandesTrajetsAdapter? {
        return adapter
    }

}
