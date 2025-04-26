package com.example.android_bootcamp.domain.model

import kotlinx.serialization.SerialName

data class Book(
    val id: String,
    val imageUrl: String,
    val title: String,
    val rating: Double,
    val averagePrice: Double,
    val aboutBook: String,
    val author: String,
    val aboutAuthor: String,
    val genres: String,
    val source: String?
)
