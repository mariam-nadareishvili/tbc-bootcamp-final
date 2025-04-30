package com.tbc.bookli.feature.register

data class RegisterUiState(
    val email: String = "",
    val fullName: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val isPasswordSame: Boolean = false,
    val isRepeatPasswordVisible: Boolean = false,
    val userId: String = "ae9aff30-a102-4e36-bfc3-70d0a87efde9" // Hardcoded user id due to api restrictions
)