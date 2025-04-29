package com.tbc.bookli.presentation.screen.search

import com.tbc.bookli.domain.model.BookStatus
import kotlinx.serialization.Serializable

@Serializable
data class BookUi(
    val id: String,
    val imageUrl: String,
    val title: String,
    val author: String,
    val aboutAuthor: String,
    val aboutBook: String,
    val genres: List<GenreUi>,
    val source: String?,
    val readBy: String,
    val pages: Int,
    val status: BookStatus?,
    val rating: Double,
    val votes: String,
    val reviews: List<ReviewUi>
)

@Serializable
data class ReviewUi(
    val reviewerName: String,
    val date: String,
    val comment: String,
    val rating: Double,
    val avatar: String
)

@Serializable
data class GenreUi(
    val id: String,
    val name: String
)