package com.example.android_bootcamp.presentation.screen.register

sealed class RegisterUiEvents {
    data class ShowError(val message: String) : RegisterUiEvents()
    data object NavigateBackToLogin: RegisterUiEvents()
    data object OnBackPress : RegisterUiEvents()
}