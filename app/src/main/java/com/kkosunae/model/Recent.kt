package com.kkosunae.model

data class Recent(
    val createdAt: String,
    val distance: Int,
    val duration: Int,
    val endLatitude: Double,
    val endLongitude: Double,
    val endTime: String,
    val id: Int,
    val isWalking: Boolean,
    val startLatitude: Double,
    val startLongitude: Double,
    val startTime: String,
    val updatedAt: String,
    val user_id: Int
)