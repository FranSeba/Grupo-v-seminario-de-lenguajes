package com.example.tp_kotlin_grupo_v
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class Giveaway(
 var id: Int = 0,
 var nombre: String,
var sitio: String,
var tipo: String,
var precio: String,
var diasRestantes: String,
var cantidadPersonas: String,
var imagenPlaceholder: String
)
