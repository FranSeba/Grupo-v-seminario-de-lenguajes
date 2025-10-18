package com.example.tp_kotlin_grupo_v.domain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_kotlin_grupo_v.databinding.ItemGiveawayBinding
import com.squareup.picasso.Picasso

class GiveawayAdapter(
    private var giveaways: MutableList<GiveawayDTO>,
    private val onItemClick: (GiveawayDTO) -> Unit
) : RecyclerView.Adapter<GiveawayAdapter.GiveawayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiveawayViewHolder {
        val binding = ItemGiveawayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GiveawayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GiveawayViewHolder, position: Int) {
        val giveaway = giveaways[position]
        holder.bind(giveaway)
        holder.itemView.setOnClickListener {
            onItemClick(giveaway)
        }
    }

    override fun getItemCount(): Int = giveaways.size

    fun updateGiveaways(newGiveaways: List<GiveawayDTO>) {
        giveaways.clear()
        giveaways.addAll(newGiveaways)
        notifyDataSetChanged()
    }

    class GiveawayViewHolder(private val binding: ItemGiveawayBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(giveaway: GiveawayDTO) {
            binding.tvTitle.text = giveaway.title
            binding.tvDescription.text = giveaway.description
            binding.tvWorth.text = giveaway.worth
            binding.tvType.text = giveaway.type
            binding.tvPlatform.text = giveaway.platforms
            binding.tvEndDate.text = "Finaliza: ${giveaway.endDate}"

            Picasso.with(binding.root.context)
                .load(giveaway.thumbnail)
                .into(binding.ivGameImage)
        }
    }
}
