package com.example.android_bootcamp.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDto(
    val id: String,
    val imageUrl: String,
    val title: String,
    val rating: Double,
    @SerialName("average_price") val averagePrice: Double,
    @SerialName("about_book") val aboutBook: String,
    val author: String,
    @SerialName("about_author") val aboutAuthor: String,
    val genres: String,
    val source: String?
)