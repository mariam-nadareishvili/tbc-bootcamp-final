package com.tbc.bookli.feature.login

sealed class LoginEvent {
    data class EmailChanged(val email: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data class RememberMeChanged(val checked: Boolean) : LoginEvent()
    data object TogglePasswordVisibility : LoginEvent()
    data object Login : LoginEvent()
    data object NavigateToRegister : LoginEvent()
}
