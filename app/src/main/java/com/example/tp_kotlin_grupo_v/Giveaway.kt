package com.example.tp_kotlin_grupo_v
import com.example.tp_kotlin_grupo_v.dtos.GiveawayDTO
import com.squareup.moshi.JsonClass
import java.text.SimpleDateFormat
import java.util.Locale

@JsonClass(generateAdapter = true)
data class Giveaway(
 val id: Int,
 val title: String,
 val shortDescription: String,
 val genre: String,
 val platform: String,
 val developer: String,
 val publisher: String,
 val releaseDate: String,
 val imageUrl: String // La URL para Picasso/Glide
)


fun GiveawayDTO.toGiveawayModel(): Giveaway {

  val displayDate = try {
  val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
  val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
  val date = inputFormat.parse(this.release)
  outputFormat.format(date)
 } catch (e: Exception) {
  this.release
 }

 return Giveaway(
  id = this.id,
  title = this.title,
  shortDescription = this.description,
  genre = this.genre,
  platform = this.platform.replace(" (Windows)", ""),
  developer = "Developer: ${this.developer}",
  publisher = "Publisher: ${this.publisher}",
  releaseDate = "Released: $displayDate",
  imageUrl = this.thumbnail
 )
}