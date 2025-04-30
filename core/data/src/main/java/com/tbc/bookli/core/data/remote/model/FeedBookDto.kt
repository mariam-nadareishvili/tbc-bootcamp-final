package com.tbc.bookli.core.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class FeedBookDto(
    val id: String,
    val imageUrl: String,
    val title: String,
    val rating: Double
)