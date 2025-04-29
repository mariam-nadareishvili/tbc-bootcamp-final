package com.tbc.bookli.presentation.screen.profile

import com.tbc.bookli.presentation.screen.AvatarType

sealed class ProfileEvent {
    data class ToggleTheme(val isDarkMode: Boolean) : ProfileEvent()
    data object ToggleLanguage : ProfileEvent()
    data object FetchUserInfo : ProfileEvent()
    data class UpdateUserAvatar(val newAvatar: AvatarType) : ProfileEvent()
    data object ShowDialog : ProfileEvent()
    data object HideDialog : ProfileEvent()
    data object ClearPreferences : ProfileEvent()
    data object NavigateToLogin : ProfileEvent()
}
