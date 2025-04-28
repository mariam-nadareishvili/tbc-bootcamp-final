package com.tbc.bookli.data.mapper

import com.tbc.bookli.data.remote.model.UserDto
import com.tbc.bookli.domain.model.User

fun UserDto.toDomain(): User {
    return User(firstName = firstName, lastName = lastName)
}