package com.sm.travelapp.data

data class Profile(
    val name: String,
    val email: String,
    val imageUrl: String? = null
)

data class BookedDestination(
    val product: Product,
    val bookingDate: String,
    val numberOfPeople: Int,
    val totalPrice: Double,
    val status: BookingStatus = BookingStatus.UPCOMING
)

enum class BookingStatus {
    UPCOMING,
    COMPLETED,
    CANCELLED
} 