package com.example.android_bootcamp.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class StoryDto(
    val id: String,
    val quote: String,
    val imageUrl: String
)
