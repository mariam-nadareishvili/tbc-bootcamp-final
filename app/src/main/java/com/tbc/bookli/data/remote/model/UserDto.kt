package com.tbc.bookli.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val fullName: String,
    val email: String,
    val avatar: String
)
