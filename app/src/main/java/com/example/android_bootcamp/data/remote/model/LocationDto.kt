package com.example.android_bootcamp.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val latitude: Double,
    val longitude: Double,
    val title: String,
    val address: String
)