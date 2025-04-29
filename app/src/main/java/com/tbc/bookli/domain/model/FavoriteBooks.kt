package com.tbc.bookli.domain.model

data class FavoriteBooks(
    val favorites: List<Book>,
    val readings: List<Book>
)