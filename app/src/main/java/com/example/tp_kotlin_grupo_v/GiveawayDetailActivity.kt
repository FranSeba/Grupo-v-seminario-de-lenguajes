package com.example.tp_kotlin_grupo_v

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GiveawayDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_give_away)


        val imgGiveaway: ImageView = findViewById(R.id.imgGiveaway)
        val tvTitulo: TextView = findViewById(R.id.tvTitulo)
        val tvValor: TextView = findViewById(R.id.tvValor)
        val tvEstado: TextView = findViewById(R.id.tvEstado)
        val btnReclamar: Button = findViewById(R.id.btnReclamar)
    }
}
