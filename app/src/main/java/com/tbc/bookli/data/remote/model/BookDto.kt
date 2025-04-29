package com.tbc.bookli.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookDto(
    val id: String,
    val imageUrl: String,
    val title: String,
    val author: String,
    @SerialName("about_author") val aboutAuthor: String,
    @SerialName("about_book") val aboutBook: String,
    val genres: String,
    val source: String?,
    val readBy: String,
    val pages: Int,
    val status: String? = null,
    @SerialName("rating_info") val ratingInfo: RatingInfoDto
)

@Serializable
data class RatingInfoDto(
    val rating: Double,
    val votes: String,
    val reviews: List<ReviewDto>
)

@Serializable
data class ReviewDto(
    @SerialName("reviewer_name") val reviewerName: String,
    val date: String,
    val comment: String,
    val rating: Double,
    val avatar: String
)
