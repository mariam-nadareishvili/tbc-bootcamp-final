package com.tbc.bookli.data.mapper

import com.tbc.bookli.data.remote.model.UserDto
import com.tbc.bookli.domain.model.User

fun UserDto.toDomain(): User {
    return User(id = id, fullName = fullName, email = email, avatar = avatar)
}

fun User.toDto(): UserDto {
    return UserDto(id = id, fullName = fullName, email = email, avatar = avatar)
}