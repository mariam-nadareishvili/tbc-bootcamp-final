package com.tbc.bookli.presentation.screen.register


data class RegisterUiState(
    val email: String = "",
    val fullName: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isPasswordSame: Boolean = false,
    val isRepeatPasswordVisible: Boolean = false
)