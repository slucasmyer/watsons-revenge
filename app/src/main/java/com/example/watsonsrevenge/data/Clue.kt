package com.example.watsonsrevenge.data

data class Clue(
    val id: Int,
    val name: String,
    val description: String,
    val hint: String,
    val info: String,
    val latitude: Double,
    val longitude: Double
)
