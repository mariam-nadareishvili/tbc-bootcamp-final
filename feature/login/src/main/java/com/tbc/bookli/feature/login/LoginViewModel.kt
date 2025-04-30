package com.tbc.bookli.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbc.bookli.core.common.R
import com.tbc.bookli.core.ui.SnackbarManager
import com.tbc.bookli.core.ui.UiText
import com.tbc.bookli.core.domain.Resource
import com.tbc.bookli.core.domain.usecase.EmailValidationUseCase
import com.tbc.bookli.core.domain.usecase.LoginUseCase
import com.tbc.bookli.core.domain.usecase.PasswordValidationUseCase
import com.tbc.bookli.core.domain.usecase.SaveRememberMeUseCase
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
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val emailValidationUseCase: EmailValidationUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase,
    private val saveRememberMeUseCase: SaveRememberMeUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState = _loginState.asStateFlow()

    private val _uiEvents = MutableSharedFlow<LoginUiEvents>()
    val uiEvents = _uiEvents.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                _loginState.update { it.copy(email = event.email) }
            }

            is LoginEvent.PasswordChanged -> {
                _loginState.update { it.copy(password = event.password) }
            }

            is LoginEvent.RememberMeChanged -> {
                _loginState.update { it.copy(isRememberMeChecked = event.checked) }
            }

            is LoginEvent.TogglePasswordVisibility -> {
                _loginState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }

            is LoginEvent.Login -> {
                performLogin()
            }

            is LoginEvent.NavigateToRegister -> {
                viewModelScope.launch {
                    _uiEvents.emit(LoginUiEvents.NavigateToRegister)
                }
            }
        }
    }

    private fun performLogin() {
        viewModelScope.launch(Dispatchers.IO) {
            val state = _loginState.value
            if (!emailValidationUseCase(state.email)) {
                SnackbarManager.showMessage(UiText.StringResource(R.string.invalid_email))
                return@launch
            }
            if (!passwordValidationUseCase(state.password)) {
                SnackbarManager.showMessage(UiText.StringResource(R.string.invalid_password))
                return@launch
            }

            loginUseCase(
                email = state.email,
                password = state.password,
                isRememberedMeChecked = state.isRememberMeChecked
            ).collect { result ->
                when (result) {
                    is Resource.Loading -> _loginState.update { it.copy(isLoading = result.isLoading) }

                    is Resource.Success -> {
                        if (_loginState.value.isRememberMeChecked) {
                            saveRememberMeUseCase(true)
                        }

                        _uiEvents.emit(LoginUiEvents.NavigateToHomeScreen)
                    }

                    is Resource.Error -> {
                        SnackbarManager.showMessage(UiText.DynamicString(result.message))
                    }
                }
            }
        }
    }
}

sealed class LoginUiEvents {
    data object NavigateToHomeScreen : LoginUiEvents()
    data object NavigateToRegister : LoginUiEvents()
}