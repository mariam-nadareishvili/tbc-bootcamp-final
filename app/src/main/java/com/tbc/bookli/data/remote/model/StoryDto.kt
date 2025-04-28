package com.tbc.bookli.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class StoryDto(
    val id: String,
    val quote: String,
    val imageUrl: String
)
