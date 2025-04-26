package com.example.android_bootcamp.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.common.Resource
import com.example.android_bootcamp.domain.useCase.EmailValidationUseCase
import com.example.android_bootcamp.domain.useCase.PasswordValidationUseCase
import com.example.android_bootcamp.domain.useCase.RegisterUseCase
import com.example.android_bootcamp.presentation.screen.register.RegisterUiEvents
import com.example.android_bootcamp.presentation.screen.register.RegisterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val emailValidationUseCase: EmailValidationUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase
) : ViewModel() {

    private val _registerState =
        MutableStateFlow(RegisterUiState())
    val registerState get() = _registerState.asStateFlow()

    private val _uiEvents = MutableSharedFlow<RegisterUiEvents>()
    val uiEvents: SharedFlow<RegisterUiEvents> = _uiEvents

    fun register() {
        viewModelScope.launch(Dispatchers.IO) {
            when {
                !emailValidationUseCase(email = registerState.value.email) -> {
                    _uiEvents.emit(RegisterUiEvents.ShowError("Invalid email!"))
                    return@launch
                }

                !passwordValidationUseCase(password = registerState.value.password) -> {
                    _uiEvents.emit(RegisterUiEvents.ShowError("Invalid Password!"))
                    return@launch
                }

                registerState.value.repeatPassword != registerState.value.password -> {
                    _uiEvents.emit(RegisterUiEvents.ShowError("Password doesn't match!"))
                    return@launch
                }
            }


            registerUseCase(
                email = registerState.value.email,
                password = registerState.value.password
            ).collect { result ->
                when (result) {
                    is Resource.Error -> _uiEvents.emit(RegisterUiEvents.ShowError(message = result.message))
                    is Resource.Loading -> _registerState.update {
                        it.copy(isLoading = result.isLoading)
                    }

                    is Resource.Success -> {
                        _registerState.update {
                            it.copy(
                            )
                        }
                        _uiEvents.emit(
                            RegisterUiEvents.NavigateBackToLogin

                        )
                    }
                }
            }
        }
    }

    fun updateEmail(newEmail: String) {
        _registerState.update { it.copy(email = newEmail) }
    }

    fun updateFullName(newFullName: String) {
        _registerState.update { it.copy(fullName = newFullName) }
    }

    fun updatePassword(newPassword: String) {
        _registerState.update {
            it.copy(
                password = newPassword,
                isPasswordSame = newPassword == it.repeatPassword
            )
        }
    }

    fun updateRepeatPassword(newRepeatPassword: String) {
        _registerState.update {
            it.copy(
                repeatPassword = newRepeatPassword,
                isPasswordSame = newRepeatPassword == it.password
            )
        }
    }

    fun togglePasswordVisibility() {
        _registerState.update {
            it.copy(
                isPasswordVisible = !_registerState.value.isPasswordVisible
            )
        }
    }

    fun toggleRepeatPasswordVisibility() {
        _registerState.update {
            it.copy(
                isRepeatPasswordVisible = !_registerState.value.isRepeatPasswordVisible
            )
        }
    }

    fun navigateWithBackIcon() {
        viewModelScope.launch {
            _uiEvents.emit(RegisterUiEvents.OnBackPress)
        }
    }
}