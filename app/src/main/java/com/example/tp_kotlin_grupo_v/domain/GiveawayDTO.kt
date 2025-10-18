package com.example.tp_kotlin_grupo_v.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GiveawayDTO(
    var id: Int = 0,
    var title: String,
    var worth: String,
    var thumbnail: String,
    var image: String,
    var description: String,
    var instructions: String,
    @field:Json(name = "open_giveaway_url")
    var openGiveawayUrl: String,
    @field:Json(name = "published_date")
    var publishedDate: String,
    var type: String,
    var platforms: String,
    @field:Json(name = "end_date")
    var endDate: String,
    var users: Int,
    var status: String,
    @field:Json(name = "gamerpower_url")
    var gamerpowerUrl: String
)
