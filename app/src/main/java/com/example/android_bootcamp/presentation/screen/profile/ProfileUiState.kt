package com.example.android_bootcamp.presentation.screen.profile

data class ProfileUiState(
    val isDarkMode: Boolean = false,
    val currentLanguage: String = LanguageType.ENGLISH.language
)