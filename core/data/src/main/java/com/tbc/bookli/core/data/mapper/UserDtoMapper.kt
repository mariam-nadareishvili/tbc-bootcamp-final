package com.tbc.bookli.core.data.mapper

import com.tbc.bookli.core.data.remote.model.UserDto
import com.tbc.bookli.core.domain.model.User

fun UserDto.toDomain(): User {
    return User(id = id, fullName = fullName, email = email, avatar = avatar)
}

fun User.toDto(): UserDto {
    return UserDto(id = id, fullName = fullName, email = email, avatar = avatar)
}