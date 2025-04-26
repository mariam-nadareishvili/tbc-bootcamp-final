package com.example.android_bootcamp.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val firstName: String? = null,
    val lastName: String? = null,
)
