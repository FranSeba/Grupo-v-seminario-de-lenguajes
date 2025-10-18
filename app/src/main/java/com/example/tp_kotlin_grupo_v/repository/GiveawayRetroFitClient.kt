package com.example.tp_kotlin_grupo_v.repository

import com.example.tp_kotlin_grupo_v.model.GiveawayDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface GiveawayRetroFitClient {


    @GET("giveaways")
    suspend fun getGiveaways(): List<GiveawayDTO>

    @GET("giveaway")
    suspend fun getGameDetails(@Query("id") giveawayId: Int): GiveawayDTO


}