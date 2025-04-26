package com.example.android_bootcamp.presentation.mapper

import com.example.android_bootcamp.data.remote.model.UserDto
import com.example.android_bootcamp.domain.model.User
import com.example.android_bootcamp.presentation.screen.profile.UserUi
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun User.toPresentation(): UserUi {
    return UserUi(
        firstname = firstName ?: "Unknown",
        lastname = lastName ?: "Unknown"
    )
}
