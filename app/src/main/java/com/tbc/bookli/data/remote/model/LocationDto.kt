package com.tbc.bookli.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    val latitude: Double,
    val longitude: Double,
    val title: String,
    val address: String
)