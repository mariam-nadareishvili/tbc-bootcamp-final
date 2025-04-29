package com.tbc.bookli.domain.model

data class Book(
    val id: String,
    val imageUrl: String,
    val title: String,
    val author: String,
    val aboutAuthor: String,
    val aboutBook: String,
    val genres: String,
    val source: String?,
    val readBy: String,
    val pages: Int,
    val status: String?,
    val rating: Double,
    val votes: String,
    val reviews: List<Review>
)

data class Review(
    val reviewerName: String,
    val date: String,
    val comment: String,
    val rating: Double,
    val avatar: String
)

