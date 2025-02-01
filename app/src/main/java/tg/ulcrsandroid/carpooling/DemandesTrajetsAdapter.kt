package tg.ulcrsandroid.carpooling

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class DemandesTrajets(
    var passager: String,
    val depart: String,
    val destination: String,
    val distance: Double,
    var etat: Int = 0
) : Parcelable {

    // Constructeur utilisé pour créer un objet à partir d'un Parcel
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble() ?: 0.0,
        parcel.readInt() ?: 0
    )

    // Méthode pour écrire l'objet dans un Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(passager)
        parcel.writeString(depart)
        parcel.writeString(destination)
        parcel.writeDouble(distance)
        parcel.writeInt(etat)
    }

    // Méthode pour décrire le contenu de l'objet (utilisé pour les cas spéciaux)
    override fun describeContents(): Int {
        return 0
    }

    // Companion object pour créer un objet à partir d'un Parcel
    companion object CREATOR : Parcelable.Creator<DemandesTrajets> {
        override fun createFromParcel(parcel: Parcel): DemandesTrajets {
            return DemandesTrajets(parcel)
        }

        override fun newArray(size: Int): Array<DemandesTrajets?> {
            return arrayOfNulls(size)
        }
    }
}

class DemandesTrajetsAdapter(private val demandesTrajets: List<DemandesTrajets>,
                             private val onItemClick: (DemandesTrajets, Int) -> Unit,
                             private val onDeleteClick: (DemandesTrajets, Int) -> Unit
) : RecyclerView.Adapter<DemandesTrajetsAdapter.DemandesTrajetsViewHolder>()   {

    inner class DemandesTrajetsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val passager: TextView = itemView.findViewById(R.id.passager)
        val trajet: TextView = itemView.findViewById(R.id.trajet)
        val heure: TextView = itemView.findViewById(R.id.heure)
        val date: TextView = itemView.findViewById(R.id.date)
        val etatIcon: ImageView  = itemView.findViewById(R.id.etat_icon)
        val clicView: LinearLayout = itemView.findViewById(R.id.clic_container)
        val chaticon: ImageView = itemView.findViewById(R.id.chat_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemandesTrajetsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_demande_trajet, parent, false)
        return DemandesTrajetsViewHolder(view)
    }

    override fun onBindViewHolder(holder: DemandesTrajetsViewHolder, position: Int) {
        val demandeTrajet = demandesTrajets[position]
        holder.passager.text = demandeTrajet.passager
        holder.trajet.text = "${demandeTrajet.depart} - ${demandeTrajet.destination}"
        holder.heure.text = "${demandeTrajet.distance}km"
        holder.date.text = "${demandeTrajet.distance}km"

        when (demandeTrajet.etat) {
            1 -> holder.etatIcon.setImageResource(R.drawable.checked_icon)
            -1 -> holder.etatIcon.setImageResource(R.drawable.error_icon)
            else -> holder.etatIcon.setImageResource(R.drawable.attente_icon)
        }

        // Désactiver le clic si la demande est déjà validée ou refusée
        if (demandeTrajet.etat == 1 || demandeTrajet.etat == -1) {
            holder.clicView.isClickable = false
            holder.clicView.isFocusable = false
        } else {
            holder.clicView.isClickable = true
            holder.clicView.isFocusable = true
        }

        holder.clicView.setOnClickListener {
            if (demandeTrajet.etat == 0) { // Ne permettre le clic que si l'état est 0 (en attente)
                onItemClick(demandeTrajet, position)
            }
        }

        holder.chaticon.setOnClickListener {
            onDeleteClick(demandeTrajet, position)
        }
    }

    // Retourner le nombre d'éléments dans la liste
    override fun getItemCount(): Int {
        return demandesTrajets.size
    }

    fun updateItem(position: Int, newEtat: Int) {
        demandesTrajets[position].etat = newEtat
        notifyItemChanged(position)
    }

}