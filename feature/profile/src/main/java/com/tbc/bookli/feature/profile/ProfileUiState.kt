package com.tbc.bookli.feature.profile

import com.tbc.bookli.core.ui.model.UserUi

data class ProfileUiState(
    val isLoading: Boolean = false,
    val isDarkMode: Boolean = false,
    val currentLanguage: String = LanguageType.ENGLISH.language,
    val userInfo: UserUi? = null,
    val showDialog: Boolean = false,
    val userId: String = "ae9aff30-a102-4e36-bfc3-70d0a87efde9" // Hardcoded user id due to api restrictions
)