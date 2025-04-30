package com.tbc.bookli.core.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class GenreDto(
    val id: String,
    val name: String
)
