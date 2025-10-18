package com.example.tp_kotlin_grupo_v.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_kotlin_grupo_v.R
import com.example.tp_kotlin_grupo_v.adapters.GiveawayAdapter
import com.example.tp_kotlin_grupo_v.configurations.RetrofitClient
import com.example.tp_kotlin_grupo_v.dtos.GiveawayDTO
import com.example.tp_kotlin_grupo_v.endpoints.ApiEndpoints
import com.example.tp_kotlin_grupo_v.toGiveawayModel
import retrofit2.Call
import retrofit2.Response

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

        val api = RetrofitClient.retrofit.create(ApiEndpoints::class.java)
        val callGetGiveaways = api.getGiveaways()

        callGetGiveaways.enqueue(object : retrofit2.Callback<List<GiveawayDTO>> {
            override fun onResponse(call: Call<List<GiveawayDTO>>, response: Response<List<GiveawayDTO>>) {
                val giveaways = response.body()
                if (giveaways != null) {
                    //Aca le ponemos las cosas al rv_giveaway
                    val mappedGiveaways = giveaways.map { it.toGiveawayModel() }.toMutableList()
                    giveawayAdapter.updateList(mappedGiveaways)
                    Log.i("MainActivity", "Lista de Giveaways actualizada con ${giveaways.size} elementos.")
                }
            }

            override fun onFailure(call: Call<List<GiveawayDTO>>, t: Throwable) {
                Log.e("MainActivity", "Error al obtener giveaways", t)
            }
        })



        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = resources.getString(R.string.toolbar_titulo)

        rvGiveaways = findViewById(R.id.rv_giveaways)


    }

    private fun fetchGiveaways() {
        val api = RetrofitClient.retrofit.create(ApiEndpoints::class.java)
        val call: Call<List<GiveawayDTO>> = api.getGiveaways()

        call.enqueue(object : retrofit2.Callback<List<GiveawayDTO>> {
            override fun onResponse(call: Call<List<GiveawayDTO>>, response: Response<List<GiveawayDTO>>) {
                if (response.isSuccessful) {
                    val apiGiveaways: List<GiveawayDTO>? = response.body()

                    if (apiGiveaways != null) {
                        val mappedGiveaways = apiGiveaways.map { it.toGiveawayModel() }.toMutableList()
                        giveawayAdapter.updateList(mappedGiveaways)
                        Log.i("MainActivity", "Lista de Giveaways actualizada con ${mappedGiveaways.size} elementos.")
                    }
                } else {
                    Log.e("Error", "Respuesta no exitosa: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<GiveawayDTO>>, t: Throwable) {
                Log.e("Error", "Fallo en la petición: ${t.message}")
            }
        })
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