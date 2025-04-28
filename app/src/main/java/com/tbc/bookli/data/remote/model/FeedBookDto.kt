package com.tbc.bookli.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeedBookDto(
    val id: String,
    val imageUrl: String,
    val title: String,
    val rating: Double,
    @SerialName("average_price") val averagePrice: Double
)