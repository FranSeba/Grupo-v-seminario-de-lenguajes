package com.example.tp_kotlin_grupo_v.presentation.ui.lista

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_kotlin_grupo_v.R
import com.example.tp_kotlin_grupo_v.domain.GiveawayAdapter
import com.example.tp_kotlin_grupo_v.presentation.GiveawayDetailActivity
import com.example.tp_kotlin_grupo_v.repository.GiveawayRetroFitClientImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListaFragment : Fragment() {

    private lateinit var rvGiveaways: RecyclerView
    private lateinit var giveawayAdapter: GiveawayAdapter
    private lateinit var tvLoading: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lista, container, false)

        rvGiveaways = view.findViewById(R.id.rv_giveaways)
        tvLoading = view.findViewById(R.id.tv_loading)

        setupRecyclerView()
        fetchGiveaways()

        return view
    }

    private fun setupRecyclerView() {
        rvGiveaways.layoutManager = LinearLayoutManager(requireContext())
        giveawayAdapter = GiveawayAdapter(mutableListOf()) { giveaway ->
            val intent = Intent(requireContext(), GiveawayDetailActivity::class.java).apply {
                putExtra("GIVEAWAY_ID", giveaway.id)
            }
            startActivity(intent)
        }
        rvGiveaways.adapter = giveawayAdapter
    }

    private fun fetchGiveaways() {
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            showLoading(true)
            try {
                val giveaways = withContext(Dispatchers.IO) {
                    GiveawayRetroFitClientImpl.client.getGiveaways()
                }
                giveawayAdapter.updateGiveaways(giveaways)
            } catch (e: Exception) {
                Log.e("ListaFragment", "Error fetching giveaways", e)
            } finally {
                showLoading(false)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        tvLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        rvGiveaways.visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}
