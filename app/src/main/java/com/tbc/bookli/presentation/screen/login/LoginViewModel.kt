package com.tbc.bookli.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbc.bookli.common.Resource
import com.tbc.bookli.domain.useCase.EmailValidationUseCase
import com.tbc.bookli.domain.useCase.LoginUseCase
import com.tbc.bookli.domain.useCase.PasswordValidationUseCase
import com.tbc.bookli.presentation.screen.login.LoginUiEvents
import com.tbc.bookli.presentation.screen.login.LoginUiState
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
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val emailValidationUseCase: EmailValidationUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState get() = _loginState.asStateFlow()

    private val _uiEvents = MutableSharedFlow<LoginUiEvents>()
    val uiEvents: SharedFlow<LoginUiEvents> = _uiEvents


    fun login() {
        viewModelScope.launch(Dispatchers.IO) {
            when {
                !emailValidationUseCase(loginState.value.email) -> {
                    _uiEvents.emit(LoginUiEvents.ShowError("Invalid Email!"))
                    return@launch
                }

                !passwordValidationUseCase(loginState.value.password) -> {
                    _uiEvents.emit(LoginUiEvents.ShowError("Invalid Password!"))
                    return@launch
                }
            }

            loginUseCase(
                email = loginState.value.email,
                password = loginState.value.password,
                isRememberedMeChecked = loginState.value.isRememberMeChecked
            ).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _uiEvents.emit(LoginUiEvents.ShowError(message = result.message))
                    }
                    is Resource.Loading -> _loginState.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> {
                        _uiEvents.emit(
                            LoginUiEvents.NavigateToHomeScreen
                        )
                    }
                }
            }
        }
    }

    fun updateEmail(email: String) {
        _loginState.update { it.copy(email = email) }
    }

    fun updatePassword(password: String) {
        _loginState.update { it.copy(password = password) }
    }

    fun updateRememberMe(isChecked: Boolean) {
        _loginState.update { it.copy(isRememberMeChecked = isChecked) }
    }

    fun togglePasswordVisibility() {
        _loginState.update { it.copy(isPasswordVisible = !_loginState.value.isPasswordVisible) }
    }

    fun navigateToRegister() {
        viewModelScope.launch {
            _uiEvents.emit(LoginUiEvents.NavigateToRegister)
        }
    }

}