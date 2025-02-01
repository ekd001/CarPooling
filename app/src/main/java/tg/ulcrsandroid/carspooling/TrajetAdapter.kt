package tg.ulcrsandroid.carspooling

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import tg.ulcrsandroid.carspooling.R
import tg.ulcrsandroid.carspooling.core.models.RideModel
import tg.ulcrsandroid.carspooling.domain.entities.Ride

class TrajetAdapter : ListAdapter<RideModel, TrajetAdapter.TrajetViewHolder>(DIFF_CALLBACK) {

    class TrajetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val trajetDate: TextView = itemView.findViewById(R.id.trajetDate)
        val trajetTime: TextView = itemView.findViewById(R.id.trajetTime)
        val trajetDepart: TextView = itemView.findViewById(R.id.trajetDepart)
        val trajetDestination: TextView = itemView.findViewById(R.id.trajetDestination)
        val trajetPlaces: TextView = itemView.findViewById(R.id.trajetPlaces)

        fun bind(trajet: RideModel) {
            trajetDate.text = trajet.dateRide
            trajetTime.text = trajet.hoursRide ?:"Heure non défini"
            trajetDepart.text = "${trajet.departure}"
            trajetDestination.text = "${trajet.arrival}"
            trajetPlaces.text = "Places : ${trajet.placeNumber ?: "N/A"}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrajetViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_trajet, parent, false)
        return TrajetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TrajetViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RideModel>() {
            override fun areItemsTheSame(oldItem: RideModel, newItem: RideModel): Boolean {
                return oldItem.rideId == newItem.rideId // Comparaison par ID unique
            }

            override fun areContentsTheSame(oldItem: RideModel, newItem: RideModel): Boolean {
                return oldItem == newItem // Comparaison complète des objets
            }
        }
    }
}