package com.example.tp_kotlin_grupo_v.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_kotlin_grupo_v.R
import com.example.tp_kotlin_grupo_v.model.GiveawayAdapter
import com.example.tp_kotlin_grupo_v.model.GiveawayDTO
import com.example.tp_kotlin_grupo_v.repository.GiveawayRetroFitClientImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var rvGiveaways: RecyclerView
    private lateinit var toolbar: Toolbar
    private lateinit var giveawayAdapter: GiveawayAdapter
    private lateinit var tvLoading: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = resources.getString(R.string.toolbar_titulo)

        rvGiveaways = findViewById(R.id.rv_giveaways)
        tvLoading = findViewById(R.id.tv_loading)

        setupRecyclerView()
        fetchGiveaways()
    }

    private fun setupRecyclerView() {
        rvGiveaways.layoutManager = LinearLayoutManager(this)
        giveawayAdapter = GiveawayAdapter(mutableListOf())
        rvGiveaways.adapter = giveawayAdapter
    }

    private fun fetchGiveaways() {
        lifecycleScope.launch(Dispatchers.Main) {
            showLoading(true)
            try {
                val giveaways = withContext(Dispatchers.IO) {
                    GiveawayRetroFitClientImpl.client.getGiveaways()
                }
                giveawayAdapter.updateGiveaways(giveaways)
            } catch (e: Exception) {
                Log.e("MainActivity", "Error fetching giveaways", e)
            } finally {
                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        tvLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        rvGiveaways.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_logout) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
