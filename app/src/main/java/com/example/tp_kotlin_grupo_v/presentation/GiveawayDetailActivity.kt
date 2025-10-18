package com.example.tp_kotlin_grupo_v.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.tp_kotlin_grupo_v.R
import com.example.tp_kotlin_grupo_v.domain.GiveawayDTO
import com.example.tp_kotlin_grupo_v.repository.GiveawayRetroFitClientImpl
import com.google.android.material.appbar.MaterialToolbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GiveawayDetailActivity : AppCompatActivity() {

    private lateinit var pbLoading: ProgressBar
    private lateinit var contentScrollView: ScrollView

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

        pbLoading = findViewById(R.id.pbLoading)
        contentScrollView = findViewById(R.id.contentScrollView)

        val giveawayId = intent.getIntExtra("GIVEAWAY_ID", 0)

        if (giveawayId != 0) {
            fetchGiveawayDetails(giveawayId)
        }
    }

    private fun fetchGiveawayDetails(giveawayId: Int) {
        lifecycleScope.launch(Dispatchers.Main) {
            showLoading(true)
            try {
                val giveaway = withContext(Dispatchers.IO) {
                    GiveawayRetroFitClientImpl.client.getGameDetails(giveawayId)
                }
                llenarGiveawayDetails(giveaway)
            } catch (e: Exception) {
                Log.e("GiveawayDetailActivity", "Error fetching giveaway details", e)
            } finally {
                showLoading(false)
            }
        }
    }

    private fun llenarGiveawayDetails(giveaway: GiveawayDTO) {
        val imgGiveaway: ImageView = findViewById(R.id.imgGiveaway)
        val tvTitulo: TextView = findViewById(R.id.tvTitulo)
        val tvValor: TextView = findViewById(R.id.tvValor)
        val tvEstado: TextView = findViewById(R.id.tvEstado)
        val tvDescription: TextView = findViewById(R.id.tvDescription)
        val tvInstructions: TextView = findViewById(R.id.tvInstructions)
        val tvPublishedDate: TextView = findViewById(R.id.tvPublishedDate)
        val tvEndDate: TextView = findViewById(R.id.tvEndDate)
        val tvUsers: TextView = findViewById(R.id.tvUsers)
        val tvPlatforms: TextView = findViewById(R.id.tvPlatforms)
        val tvType: TextView = findViewById(R.id.tvType)
        val btnReclamar: Button = findViewById(R.id.btnReclamar)

        tvTitulo.text = giveaway.title
        tvValor.text = giveaway.worth
        tvEstado.text = giveaway.status
        tvDescription.text = giveaway.description
        tvInstructions.text = giveaway.instructions
        tvPublishedDate.text = "Publicado: ${giveaway.publishedDate}"
        tvEndDate.text = "Termina: ${giveaway.endDate}"
        tvUsers.text = "Participantes: ${giveaway.users}"
        tvPlatforms.text = "Plataformas: ${giveaway.platforms}"
        tvType.text = "Tipo: ${giveaway.type}"

        Picasso.with(this)
            .load(giveaway.image)
            .into(imgGiveaway)

        btnReclamar.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(giveaway.gamerpowerUrl))
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        contentScrollView.visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}
