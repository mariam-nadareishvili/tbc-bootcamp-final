package com.tbc.bookli.core.domain.model

data class FavoriteBooks(
    val favorites: List<Book>,
    val readings: List<Book>
)