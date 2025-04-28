package com.tbc.bookli.presentation.screen.search

data class BookUi(
    val id: String,
    val imageUrl: String,
    val title: String,
    val rating: Double,
    val averagePrice: Double,
    val aboutBook: String,
    val author: String,
    val aboutAuthor: String,
    val genres: List<GenreUi>,
    val source: String?
)