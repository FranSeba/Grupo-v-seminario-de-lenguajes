package com.example.tp_kotlin_grupo_v

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GiveawayAdapter(var giveaways: MutableList<Giveaway>, var context: Context) : RecyclerView.Adapter<GiveawayAdapter.GiveawayViewHolder>() {

    class GiveawayViewHolder (view: View) : RecyclerView.ViewHolder(view){
        val txtNombre: TextView
        val txtSitio: TextView
        val txtTipo: TextView
        val txtPrecio: TextView
        val txtDias: TextView
        val txtCantidadPersonas: TextView
        val imgGame: ImageView

        init {
            txtNombre = view.findViewById(R.id.tv_nombre)
            txtSitio = view.findViewById(R.id.tv_sitio)
            txtTipo = view.findViewById(R.id.tv_tipo)
            txtPrecio = view.findViewById(R.id.tv_precio)
            txtDias = view.findViewById(R.id.tv_dias)
            txtCantidadPersonas = view.findViewById(R.id.tv_cantidad_personas)
            imgGame = view.findViewById(R.id.iv_game_image)
        }
    }

    override fun getItemCount() = giveaways.size

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GiveawayViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_giveaway, viewGroup, false)
        return GiveawayViewHolder(view)
    }

    override fun onBindViewHolder(holder: GiveawayViewHolder, position: Int) {
        val item = giveaways.get(position)
        holder.txtNombre.text = item.nombre
        holder.txtSitio.text = item.sitio
        holder.txtTipo.text = item.tipo
        holder.txtPrecio.text = item.precio
        holder.txtDias.text = item.diasRestantes
        holder.txtCantidadPersonas.text = item.cantidadPersonas
        //holder.imgGame.setImageResource(item.imagenPlaceholder)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("GIVEAWAY_ID", item.id)
            context.startActivity(intent)
        }
    }

}

