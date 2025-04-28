package com.tbc.bookli.presentation.screen.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isRememberMeChecked: Boolean = false,
    val isPasswordVisible: Boolean = false
)