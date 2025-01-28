package tg.ulcrsandroid.carspooling

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tg.ulcrsandroid.carpooling.R

class ReservationAdapter(private var reservations: List<Reservation>) :
    RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>(){

    class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reservationDate: TextView = itemView.findViewById(R.id.reservationDate)
        val reservationTime: TextView = itemView.findViewById(R.id.reservationTime)
        val depart: TextView = itemView.findViewById(R.id.trajetDepartValue)
        val destination: TextView = itemView.findViewById(R.id.trajetDestinationValue)
        val reservationStatus: TextView = itemView.findViewById(R.id.reservationStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reservation, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val reservation = reservations[position]
        holder.reservationDate.text = reservation.date
        holder.reservationTime.text = reservation.time
        holder.depart.text = reservation.depart
        holder.destination.text = reservation.destination
        when (reservation.status) {
            "confirmed" -> {
                holder.reservationStatus.text = "Confirmé"
                holder.reservationStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_confirmed, 0)
                holder.reservationStatus.setBackgroundResource(R.drawable.reservation_confirmed_bg)
            }
            "pending" -> {
                holder.reservationStatus.text = "En attente"
                holder.reservationStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_pending, 0)
                holder.reservationStatus.setBackgroundResource(R.drawable.reservation_pending_bg)
                }
            "cancelled" -> {
                holder.reservationStatus.text = "Annulé"
                holder.reservationStatus.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_cancelled, 0)
                holder.reservationStatus.setBackgroundResource(R.drawable.reservation_cancelled_bg)
            }
        }
    }

    override fun getItemCount(): Int {
        return reservations.size
    }

    fun updateReservations(newReservations: List<Reservation>) {
        reservations=newReservations
        notifyDataSetChanged() // Notifie l'Adapter que les données ont changé
    }

}
