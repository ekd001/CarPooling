package tg.ulcrsandroid.carspooling

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adapter pour afficher les trajets trouvés

data class Trajet(val id: Int, val startLocation: String, val endLocation: String)
class TrajetsTrouvesAdapter(private val trajetList: List<Trajet>) : RecyclerView.Adapter<TrajetsTrouvesAdapter.TrajetViewHolder>() {

    // Crée une nouvelle vue (view holder) à partir du layout
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrajetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trajet_trouve, parent, false)
        return TrajetViewHolder(view)
    }

    // Remplir la vue avec les données
    override fun onBindViewHolder(holder: TrajetViewHolder, position: Int) {
        val trajet = trajetList[position]
        holder.startLocationText.text = trajet.startLocation
        holder.endLocationText.text = trajet.endLocation
    }

    // Retourner le nombre d'éléments dans la liste
    override fun getItemCount(): Int {
        return trajetList.size
    }

    // ViewHolder pour l'élément Trajet
    class TrajetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val startLocationText: TextView = itemView.findViewById(R.id.startLocation)
        val endLocationText: TextView = itemView.findViewById(R.id.destination)
    }
}
