package com.example.tp_kotlin_grupo_v.presentation

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tp_kotlin_grupo_v.R
import com.google.android.material.appbar.MaterialToolbar

class GiveawayDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details_give_away)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar: MaterialToolbar = findViewById(R.id.toolbar_secondary)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val imgGiveaway: ImageView = findViewById(R.id.imgGiveaway)
        val tvTitulo: TextView = findViewById(R.id.tvTitulo)
        val tvValor: TextView = findViewById(R.id.tvValor)
        val tvEstado: TextView = findViewById(R.id.tvEstado)
        val btnReclamar: Button = findViewById(R.id.btnReclamar)
    }
}