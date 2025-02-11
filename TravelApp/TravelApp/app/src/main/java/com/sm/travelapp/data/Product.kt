package com.sm.travelapp.data

data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageRes: Int,
    val rating: Float = 4.5f,
    val location: String = "",
    val duration: String = "",
    val included: List<String> = emptyList()
) 