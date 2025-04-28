package com.tbc.bookli.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val firstName: String? = null,
    val lastName: String? = null,
)
