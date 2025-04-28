package com.tbc.bookli.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    val id: String,
    val name: String
)
