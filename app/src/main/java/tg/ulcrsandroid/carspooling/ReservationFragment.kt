package tg.ulcrsandroid.carspooling

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tg.ulcrsandroid.carpooling.R
import tg.ulcrsandroid.carpooling.databinding.FragmentReservationBinding



/**
 * A simple [Fragment] subclass.
 * Use the [ReservationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReservationFragment : Fragment() {
    private lateinit var reservationAdapter: ReservationAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyReservationMessage: TextView

    private  var ui: FragmentReservationBinding? = null
    private val binding get() = ui!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ui = FragmentReservationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // configuration du recyclerView
        recyclerView = binding.reservationRecyclerView
        emptyReservationMessage = binding.emptyReservationMessage
        reservationAdapter = ReservationAdapter(emptyList())
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = reservationAdapter
        }

        //Creer une fonction pour actualiser la liste
        actualiserListe()



    }

    private fun actualiserListe() {

        val reservationList = getReservationsFromDatabase()

        if (reservationList.isEmpty()) {
            // Afficher le message si la liste est vide
            emptyReservationMessage.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            // Afficher la liste des réservations
            emptyReservationMessage.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            reservationAdapter.updateReservations(reservationList)
        }
    }

    private fun getReservationsFromDatabase(): List<Reservation> {
        // TODO: Implémenter la logique pour récupérer les réservations de la base de données

    }

    override fun onDestroyView() {
        super.onDestroyView()
        ui=null
    }
}