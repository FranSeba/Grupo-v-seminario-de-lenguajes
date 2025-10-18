package com.example.tp_kotlin_grupo_v.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_kotlin_grupo_v.Giveaway
import com.example.tp_kotlin_grupo_v.R
import com.example.tp_kotlin_grupo_v.activities.GiveawayDetailActivity
import java.text.SimpleDateFormat
import java.util.Locale

class GiveawayAdapter(
    private var giveaways: MutableList<Giveaway>,
    private val context: Context
) : RecyclerView.Adapter<GiveawayAdapter.GiveawayViewHolder>() {

    // El ViewHolder ahora coincide con el nuevo item_giveaway.xml
    class GiveawayViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gameImage: ImageView = view.findViewById(R.id.iv_game_image)
        val title: TextView = view.findViewById(R.id.tv_title)
        val description: TextView = view.findViewById(R.id.tv_description)
        val genre: TextView = view.findViewById(R.id.tv_genre)
        val platform: TextView = view.findViewById(R.id.tv_platform)
        val developer: TextView = view.findViewById(R.id.tv_developer)
        val publisher: TextView = view.findViewById(R.id.tv_publisher)
        val releaseDate: TextView = view.findViewById(R.id.tv_release_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiveawayViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_giveaway, parent, false)
        return GiveawayViewHolder(view)
    }

    override fun onBindViewHolder(holder: GiveawayViewHolder, position: Int) {
        val giveaway = giveaways[position]

        holder.title.text = giveaway.title
        holder.description.text = giveaway.shortDescription
        holder.genre.text = giveaway.genre
        holder.platform.text = giveaway.platform
        holder.developer.text = "Developer: ${giveaway.developer}"
        holder.publisher.text = "Publisher: ${giveaway.publisher}"

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        holder.releaseDate.text = "Released: ${dateFormat.format(giveaway.releaseDate)}"


        holder.itemView.setOnClickListener {
            val intent = Intent(context, GiveawayDetailActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = giveaways.size

    // Esta función actualiza la lista y notifica al adaptador que refresque el RecyclerView
    fun updateList(newGiveaways: MutableList<Giveaway>) {
        this.giveaways.clear()
        this.giveaways.addAll(newGiveaways)
        notifyDataSetChanged()
    }
}
