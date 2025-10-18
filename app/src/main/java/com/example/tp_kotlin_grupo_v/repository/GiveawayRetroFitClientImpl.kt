package com.example.tp_kotlin_grupo_v.repository

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object GiveawayRetroFitClientImpl {

    const val BASE_URL: String = "https://gamerpower.com/api/"
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
        .build()

    val client: GiveawayRetroFitClient = retrofit.create(GiveawayRetroFitClient::class.java)

}