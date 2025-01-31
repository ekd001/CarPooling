package tg.ulcrsandroid.carspooling

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SuggestionsAdapter(
    private val suggestions: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<SuggestionsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val icon: ImageView = itemView.findViewById(R.id.itemIcon)
        val text: TextView = itemView.findViewById(R.id.itemText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_recycler_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val suggestion = suggestions[position]
        holder.text.text = suggestion
        holder.icon.setImageResource(R.drawable.maps_search_icon)
        holder.itemView.setOnClickListener { onItemClick(suggestion) }
    }

    override fun getItemCount() = suggestions.size
}