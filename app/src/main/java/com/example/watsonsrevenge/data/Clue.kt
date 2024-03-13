package com.example.watsonsrevenge.data

/*
 * Sullivan Lucas Myer
 * OSU
 * CS 492
 */

// Data class for Clue
data class Clue(
    val id: Int,
    val name: String,
    val description: String,
    val hint: String,
    val info: String,
    val latitude: Double,
    val longitude: Double
)
