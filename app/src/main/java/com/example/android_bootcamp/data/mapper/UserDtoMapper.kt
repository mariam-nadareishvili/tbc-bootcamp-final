package com.example.android_bootcamp.data.mapper

import com.example.android_bootcamp.data.remote.model.UserDto
import com.example.android_bootcamp.domain.model.User

fun UserDto.toDomain(): User {
    return User(firstName = firstName, lastName = lastName)
}