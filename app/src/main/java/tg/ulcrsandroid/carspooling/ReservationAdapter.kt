package tg.ulcrsandroid.carspooling

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tg.ulcrsandroid.carspooling.core.models.ReservationModel
import tg.ulcrsandroid.carspooling.core.utils.ReservationStatus

class ReservationAdapter() : ListAdapter<ReservationModel, ReservationAdapter.ReservationViewHolder>(DIFF_CALLBACK) {

    class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val reservationDate: TextView = itemView.findViewById(R.id.reservationDate)
        private val reservationTime: TextView = itemView.findViewById(R.id.reservationTime)
        private val depart: TextView = itemView.findViewById(R.id.trajetDepartValue)
        private val destination: TextView = itemView.findViewById(R.id.trajetDestinationValue)
        private val reservationStatus: TextView = itemView.findViewById(R.id.reservationStatus)

        fun bind(reservation: ReservationModel) {
            reservationDate.text = reservation.rideDate
            reservationTime.text = reservation.rideHeure
            depart.text = reservation.rideDeparture?: "N/A"
            destination.text = reservation.rideArrival?: "N/A"

            when (reservation.status) {
                ReservationStatus.ACCEPTED.toString() -> {
                    reservationStatus.text = "Confirmé"
                    reservationStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_confirmed, 0)
                    reservationStatus.setBackgroundResource(R.drawable.reservation_confirmed_bg)
                }
                ReservationStatus.WAITING.toString() -> {
                    reservationStatus.text = "En attente"
                    reservationStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_pending, 0)
                    reservationStatus.setBackgroundResource(R.drawable.reservation_pending_bg)
                }
                ReservationStatus.REJECTED.toString() -> {
                    reservationStatus.text = "Annulé"
                    reservationStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_cancelled, 0)
                    reservationStatus.setBackgroundResource(R.drawable.reservation_cancelled_bg)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reservation, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ReservationModel>() {
            override fun areItemsTheSame(oldItem: ReservationModel, newItem: ReservationModel): Boolean {
                return oldItem.reservationId == newItem.reservationId // Comparaison par ID unique
            }

            override fun areContentsTheSame(oldItem: ReservationModel, newItem: ReservationModel): Boolean {
                return oldItem == newItem // Comparaison complète des objets
            }
        }
    }
}
