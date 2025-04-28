package com.tbc.bookli.domain.model


data class FeedBook(
    val id: String,
    val imageUrl: String,
    val title: String,
    val rating: Double,
    val averagePrice: Double
)