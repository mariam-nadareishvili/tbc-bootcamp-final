package com.example.android_bootcamp.presentation.screen.login

sealed class LoginUiEvents {
    data class ShowError(val message: String) : LoginUiEvents()
    data object NavigateToHomeScreen : LoginUiEvents()
    data object NavigateToRegister : LoginUiEvents()
}