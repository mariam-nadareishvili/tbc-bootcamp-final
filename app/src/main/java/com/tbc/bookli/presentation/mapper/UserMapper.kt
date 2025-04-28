package com.tbc.bookli.presentation.mapper

import com.tbc.bookli.domain.model.User
import com.tbc.bookli.presentation.screen.profile.UserUi

fun User.toPresentation(): UserUi {
    return UserUi(
        firstname = firstName ?: "Unknown",
        lastname = lastName ?: "Unknown"
    )
}
