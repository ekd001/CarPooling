package tg.ulcrsandroid.carspooling

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tg.ulcrsandroid.carspooling.core.utils.GlobalUser
import tg.ulcrsandroid.carspooling.databinding.FragmentConducteurListeTrajetBinding

import tg.ulcrsandroid.carspooling.domain.entities.Ride
import tg.ulcrsandroid.carspooling.features.ridemanagement.viewModel.RideMgmtViewModel


class ConducteurListeTrajetFragment : Fragment() {
    private var _binding: FragmentConducteurListeTrajetBinding?=null
    private val binding get() = _binding!!
    private lateinit var rideMgmtViewModel: RideMgmtViewModel

    private lateinit var trajetAdapter: TrajetAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentConducteurListeTrajetBinding.inflate(inflater, container, false)
        val application = requireActivity().application as CarSpoolingApplication
        rideMgmtViewModel = ViewModelProvider(
            this,
            application.factoryRideManagement
        )[RideMgmtViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trajetAdapter = TrajetAdapter()
        recyclerView = binding.trajetRecyclerView
        recyclerView.layoutManager=LinearLayoutManager(requireContext())
        recyclerView.adapter=trajetAdapter

        GlobalUser.user?.let { rideMgmtViewModel.getlistRide(it.uid) }

        // Récupérer la liste des trajets (exemple de données factices)
        rideMgmtViewModel.rides.observe(viewLifecycleOwner) {rideList ->
            trajetAdapter.submitList(rideList)
        }

        // Initialiser l'Adapter

    }



    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}