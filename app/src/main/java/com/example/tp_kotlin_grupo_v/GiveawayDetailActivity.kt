package com.example.tp_kotlin_grupo_v

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class GiveawayDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_give_away)

        val toolbar: MaterialToolbar = findViewById(R.id.toolbar_secondary)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        // El título ya se establece en el XML a través de app:title
        // Si necesitaras cambiarlo dinámicamente, podrías hacerlo así:
        // supportActionBar?.title = "Detalles del Giveaway"

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed() // O simplemente finish() si no hay lógica de back stack compleja
        }

        val imgGiveaway: ImageView = findViewById(R.id.imgGiveaway)
        val tvTitulo: TextView = findViewById(R.id.tvTitulo)
        val tvValor: TextView = findViewById(R.id.tvValor)
        val tvEstado: TextView = findViewById(R.id.tvEstado)
        val btnReclamar: Button = findViewById(R.id.btnReclamar)
    }
}
