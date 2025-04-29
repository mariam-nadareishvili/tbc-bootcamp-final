package com.tbc.bookli.presentation.screen.register

sealed class RegisterEvent {
    data class EmailChanged(val email: String) : RegisterEvent()
    data class FullNameChanged(val fullName: String) : RegisterEvent()
    data class PasswordChanged(val password: String) : RegisterEvent()
    data class RepeatPasswordChanged(val repeatPassword: String) : RegisterEvent()
    data object TogglePasswordVisibility : RegisterEvent()
    data object ToggleRepeatPasswordVisibility : RegisterEvent()
    data object SubmitRegister : RegisterEvent()
    data object NavigateBack : RegisterEvent()
}
