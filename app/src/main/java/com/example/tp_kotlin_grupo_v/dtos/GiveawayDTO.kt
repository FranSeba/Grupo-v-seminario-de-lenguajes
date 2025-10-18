package com.example.tp_kotlin_grupo_v.dtos

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = true)
data class GiveawayDTO(
    var id: Int = 0,
    var title: String,
    var thumbnail: String,
    @field:Json(name = "short_description")
    var description: String,

    var genre: String,
    var platform: String,
    var publisher: String,
    var developer: String,
    @field:Json(name = "release_date")
    var release: String

)