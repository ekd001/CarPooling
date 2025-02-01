package tg.ulcrsandroid.carspooling

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tg.ulcrsandroid.carspooling.R
import tg.ulcrsandroid.carspooling.core.models.ReservationModel
import tg.ulcrsandroid.carspooling.core.utils.GlobalUser
import tg.ulcrsandroid.carspooling.databinding.FragmentReservationBinding


import tg.ulcrsandroid.carspooling.domain.entities.Reservation
import tg.ulcrsandroid.carspooling.features.ridemanagement.viewModel.RideMgmtViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [ReservationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReservationFragment : Fragment() {
    private lateinit var reservationAdapter: ReservationAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyReservationMessage: TextView
    private lateinit var reservations: List<ReservationModel>
    private lateinit var rideMgmtViewModel: RideMgmtViewModel
    //private lateinit var reservation: Reservation


    private lateinit var binding: FragmentReservationBinding

    companion object {
        fun newInstance(): ReservationFragment {
            return ReservationFragment()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReservationBinding.inflate(inflater, container, false)
        val application = requireActivity().application as CarSpoolingApplication
        rideMgmtViewModel = ViewModelProvider(
            this,
            application.factoryRideManagement
        )[RideMgmtViewModel::class.java]
        return binding.root
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reservations = emptyList()

        // Initialisation des vues
        initViews()

        // Configuration du RecyclerView
        setupRecyclerView()


        // Chargement des réservations
        GlobalUser.user?.let { rideMgmtViewModel.getReservationPassenger(it.uid) }

        rideMgmtViewModel.reservations.observe(viewLifecycleOwner) { reservationList ->
            if (reservationList.isNotEmpty()) {
                showReservations()
                reservationAdapter.submitList(reservationList)
            } else {
                showEmptyState()
            }
        }


    }


    private fun showReservations() {
        emptyReservationMessage.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    private fun showEmptyState() {
        emptyReservationMessage.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }

    private fun setupRecyclerView() {
        reservationAdapter = ReservationAdapter()

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = reservationAdapter
        }
    }

    private fun openReservationDetails(reservation: ReservationModel) {
//        val fragment = ReservationDetailFragment().newInstance(reservation)
//        parentFragmentManager.beginTransaction()
//            .replace(R.id.fragment_detail_reservation, fragment)
//            .addToBackStack(null)
//            .commit()
    }

    private fun initViews() {
        recyclerView = binding.reservationRecyclerView
        emptyReservationMessage = binding.emptyReservationMessage
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Libérer les ressources du RecyclerView
        recyclerView.adapter = null

    }
}