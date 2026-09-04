package com.example.futgoal.models

data class Cancha(
    val nombre: String,
    val ubicacion: String,
    val tipo: String,
    val precio: Int,
    val atributos: List<String>
)
