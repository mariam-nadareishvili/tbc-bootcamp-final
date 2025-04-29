package com.tbc.bookli.presentation.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbc.bookli.R
import com.tbc.bookli.common.Resource
import com.tbc.bookli.domain.model.User
import com.tbc.bookli.domain.useCase.EmailValidationUseCase
import com.tbc.bookli.domain.useCase.PasswordValidationUseCase
import com.tbc.bookli.domain.useCase.RegisterUseCase
import com.tbc.bookli.domain.useCase.UpdateUserInfoUseCase
import com.tbc.bookli.presentation.helper.SnackbarManager
import com.tbc.bookli.presentation.helper.UiText
import com.tbc.bookli.presentation.screen.AvatarType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val emailValidationUseCase: EmailValidationUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase,
    private val updateUserUseCase: UpdateUserInfoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterUiState())
    val state = _state.asStateFlow()

    private val _uiEvents = MutableSharedFlow<RegisterUiEvent>()
    val uiEvents = _uiEvents.asSharedFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EmailChanged -> {
                _state.update { it.copy(email = event.email) }
            }

            is RegisterEvent.FullNameChanged -> {
                _state.update { it.copy(fullName = event.fullName) }
            }

            is RegisterEvent.PasswordChanged -> {
                _state.update {
                    it.copy(
                        password = event.password,
                        isPasswordSame = event.password == it.repeatPassword
                    )
                }
            }

            is RegisterEvent.RepeatPasswordChanged -> {
                _state.update {
                    it.copy(
                        repeatPassword = event.repeatPassword,
                        isPasswordSame = event.repeatPassword == it.password
                    )
                }
            }

            RegisterEvent.TogglePasswordVisibility -> {
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }

            RegisterEvent.ToggleRepeatPasswordVisibility -> {
                _state.update { it.copy(isRepeatPasswordVisible = !it.isRepeatPasswordVisible) }
            }

            RegisterEvent.SubmitRegister -> {
                performRegister()
            }

            RegisterEvent.NavigateBack -> {
                viewModelScope.launch {
                    _uiEvents.emit(RegisterUiEvent.OnBackPress)
                }
            }
        }
    }

    private fun performRegister() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentState = _state.value

            when {
                !emailValidationUseCase(currentState.email) -> {
                    SnackbarManager.showMessage(UiText.StringResource(R.string.invalid_email))
                    return@launch
                }

                !passwordValidationUseCase(currentState.password) -> {
                    SnackbarManager.showMessage(UiText.StringResource(R.string.invalid_password))
                    return@launch
                }

                currentState.repeatPassword != currentState.password -> {
                    SnackbarManager.showMessage(UiText.StringResource(R.string.password_doesn_t_match))
                    return@launch
                }
            }

            registerUseCase(currentState.email, currentState.password).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = result.isLoading) }

                    is Resource.Success -> updateUserInfo()

                    is Resource.Error -> SnackbarManager.showMessage(UiText.DynamicString(result.message))
                }
            }
        }
    }

    private suspend fun updateUserInfo() {
        updateUserUseCase(
            User(
                id = _state.value.userId,
                fullName = _state.value.fullName,
                email = _state.value.email,
                avatar = AvatarType.RABBIT.key
            )
        ).collect { result ->
            when (result) {
                is Resource.Loading -> _state.update { it.copy(isLoading = result.isLoading) }

                is Resource.Success -> _uiEvents.emit(RegisterUiEvent.NavigateBackToLogin)

                is Resource.Error -> SnackbarManager.showMessage(UiText.DynamicString(result.message))
            }
        }
    }

    sealed class RegisterUiEvent {
        data object NavigateBackToLogin : RegisterUiEvent()
        data object OnBackPress : RegisterUiEvent()
    }
}