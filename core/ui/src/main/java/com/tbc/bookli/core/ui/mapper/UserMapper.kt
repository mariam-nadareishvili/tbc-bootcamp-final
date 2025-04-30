package com.tbc.bookli.core.ui.mapper

import com.tbc.bookli.core.domain.model.User
import com.tbc.bookli.core.ui.model.AvatarType
import com.tbc.bookli.core.ui.model.UserUi

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
