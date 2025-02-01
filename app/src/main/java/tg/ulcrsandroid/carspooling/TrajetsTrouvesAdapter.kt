package tg.ulcrsandroid.carspooling

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import tg.ulcrsandroid.carspooling.core.models.RideModel

// Adapter pour afficher les trajets trouvés


class TrajetsTrouvesAdapter(private val onItemClick: (RideModel) -> Unit) : ListAdapter<RideModel, TrajetsTrouvesAdapter.TrajetViewHolder>(DIFF_CALLBACK) {

    class TrajetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val startLocationText: TextView = itemView.findViewById(R.id.startLocation)
        val endLocationText: TextView = itemView.findViewById(R.id.destination)
        val boutonReserver: Button = itemView.findViewById(R.id.reserverButton)
        val date: TextView = itemView.findViewById(R.id.date)
        val heure: TextView = itemView.findViewById(R.id.heure)
        val prix: TextView = itemView.findViewById(R.id.prix)
        val boutonReserve: Button = itemView.findViewById(R.id.reserverButton)

        fun bind(trajet: RideModel) {
            startLocationText.text = trajet.departure
            endLocationText.text = trajet.arrival
            heure.text = trajet.hoursRide
            prix.text = trajet.price.toString()
            date.text = trajet.dateRide
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrajetViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_trajet_trouve, parent, false)
        return TrajetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TrajetViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.boutonReserver.setOnClickListener {
            onItemClick(getItem(position))
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RideModel>() {
            override fun areItemsTheSame(oldItem: RideModel, newItem: RideModel): Boolean {
                return oldItem.rideId == newItem.rideId// Comparaison par ID unique
            }

            override fun areContentsTheSame(oldItem: RideModel, newItem: RideModel): Boolean {
                return oldItem == newItem // Comparaison complète des objets
            }
        }
    }
}