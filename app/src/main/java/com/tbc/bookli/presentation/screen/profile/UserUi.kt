package com.tbc.bookli.presentation.screen.profile

import com.tbc.bookli.presentation.screen.AvatarType

data class UserUi(
    val id: String,
    val fullName: String,
    val email: String,
    val avatar: AvatarType
)