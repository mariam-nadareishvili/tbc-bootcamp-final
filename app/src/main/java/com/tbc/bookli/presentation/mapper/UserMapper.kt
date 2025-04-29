package com.tbc.bookli.presentation.mapper

import com.tbc.bookli.domain.model.User
import com.tbc.bookli.presentation.screen.AvatarType
import com.tbc.bookli.presentation.screen.profile.UserUi

fun User.toPresentation(): UserUi {
    return UserUi(
        id = id,
        fullName = fullName,
        email = email,
        avatar = AvatarType.fromKey(key = avatar)
    )
}

fun UserUi.toDomain(): User {
    return User(id = id, fullName = fullName, email = email, avatar = avatar.key)
}
