package com.tbc.bookli.presentation.screen.profile

data class ProfileUiState(
    val isLoading: Boolean = false,
    val isDarkMode: Boolean = false,
    val currentLanguage: String = LanguageType.ENGLISH.language,
    val userInfo: UserUi? = null,
    val showDialog: Boolean = false
)