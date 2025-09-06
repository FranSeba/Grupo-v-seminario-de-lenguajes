package com.example.tp_kotlin_grupo_v

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    lateinit var rvGiveaways: RecyclerView
    lateinit var toolbar: Toolbar
    lateinit var giveawayAdapter: GiveawayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = resources.getString(R.string.toolbar_titulo)

        rvGiveaways = findViewById(R.id.rv_giveaways)

        giveawayAdapter = GiveawayAdapter(getGiveaways(), this)
        rvGiveaways.adapter = giveawayAdapter

    }


    private fun getGiveaways(): MutableList<Giveaway> {
        var giveaways: MutableList<Giveaway> = ArrayList()
        giveaways.add(Giveaway(1, "Cyberpunk 2077", "(GOG)", "Juego", "Gratis", "3 días", "15.2k", "ic_game_placeholder_1"))
        giveaways.add(Giveaway(2, "The Witcher 3: Wild Hunt - Expansion Pass", "(Steam)", "DLC", "$9.99", "10 días", "25.8k", "ic_game_placeholder_2"))
        giveaways.add(Giveaway(3, "Stardew Valley", "(Steam)", "Juego", "Gratis", "7 días", "50.1k", "ic_game_placeholder_3"))
        return giveaways
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_session_options && item.itemId == R.id.action_logout) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
