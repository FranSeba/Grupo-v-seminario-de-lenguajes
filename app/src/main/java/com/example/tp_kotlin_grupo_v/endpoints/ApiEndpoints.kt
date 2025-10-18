package com.example.tp_kotlin_grupo_v.endpoints

import com.example.tp_kotlin_grupo_v.dtos.GiveawayDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndpoints {

    @GET("/games")
    fun getGiveaways(): Call<List<GiveawayDTO>>

    @GET("/game")
    fun getGameDetails(@Query("id") gameId: Int): Call<GiveawayDTO>

    @GET("/games")
    fun getGamesByCategory(@Query("category") categoryName: String): Call<List<GiveawayDTO>>

    @GET("/games")
    fun getGamesByPlatform(@Query("platform") platformName: String): Call<List<GiveawayDTO>>

    @GET("/games")
    fun getSortedGames(@Query("sort-by") sortName: String): Call<List<GiveawayDTO>>


}