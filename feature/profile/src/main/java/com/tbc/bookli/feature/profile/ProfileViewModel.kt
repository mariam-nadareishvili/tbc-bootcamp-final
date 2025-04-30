package com.tbc.bookli.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbc.bookli.core.ui.SnackbarManager
import com.tbc.bookli.core.ui.UiText
import com.tbc.bookli.core.domain.Resource
import com.tbc.bookli.core.domain.usecase.ClearRememberMeUseCase
import com.tbc.bookli.core.domain.usecase.GetAppLanguageUseCase
import com.tbc.bookli.core.domain.usecase.GetDarkModeUseCase
import com.tbc.bookli.core.domain.usecase.GetUserUserInfoCase
import com.tbc.bookli.core.domain.usecase.SetDarkModeUseCase
import com.tbc.bookli.core.domain.usecase.UpdateLanguageUseCase
import com.tbc.bookli.core.domain.usecase.UpdateUserInfoUseCase
import com.tbc.bookli.core.ui.mapper.toDomain
import com.tbc.bookli.core.ui.mapper.toPresentation
import com.tbc.bookli.core.ui.model.AvatarType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val getAppLanguageUseCase: GetAppLanguageUseCase,
    private val clearRememberMeUseCase: ClearRememberMeUseCase,
    private val getDarkModeUseCase: GetDarkModeUseCase,
    private val setDarkModeUseCase: SetDarkModeUseCase,
    private val getUserInfoUseCase: GetUserUserInfoCase,
    private val updateUserInfoUseCase: UpdateUserInfoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileUiState())
    val state: StateFlow<ProfileUiState> = _state

    private val _event = MutableSharedFlow<ProfileUiEvents>()
    val events: SharedFlow<ProfileUiEvents> = _event

    init {
        onEvent(ProfileEvent.FetchUserInfo)
        observeInitialSettings()
    }

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.ToggleTheme -> toggleTheme(event.isDarkMode)
            is ProfileEvent.ToggleLanguage -> toggleLanguage()
            is ProfileEvent.FetchUserInfo -> fetchUserInfo()
            is ProfileEvent.UpdateUserAvatar -> updateUserAvatar(event.newAvatar)
            is ProfileEvent.ShowDialog -> _state.update { it.copy(showDialog = true) }
            is ProfileEvent.HideDialog -> _state.update { it.copy(showDialog = false) }
            is ProfileEvent.NavigateToLogin -> navigateToLogin()
        }
    }

    private fun observeInitialSettings() {
        viewModelScope.launch {
            getDarkModeUseCase().collect { isDarkMode ->
                _state.update { it.copy(isDarkMode = isDarkMode) }
            }
        }
        viewModelScope.launch {
            getAppLanguageUseCase().collect { language ->
                _state.update { it.copy(currentLanguage = language) }
            }
        }
    }

    private fun toggleTheme(isDarkMode: Boolean) {
        viewModelScope.launch {
            setDarkModeUseCase(isDarkMode)
            _state.update { it.copy(isDarkMode = isDarkMode) }
        }
    }

    private fun toggleLanguage() {
        val current = _state.value.currentLanguage
        val newLang = if (current == LanguageType.GEORGIAN.language)
            LanguageType.ENGLISH.language else LanguageType.GEORGIAN.language

        viewModelScope.launch {
            updateLanguageUseCase(newLang)
            _state.update { it.copy(currentLanguage = newLang) }
        }
    }

    private fun fetchUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            getUserInfoUseCase(_state.value.userId).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> _state.update { it.copy(userInfo = result.data?.toPresentation()) }
                    is Resource.Error -> SnackbarManager.showMessage(
                        UiText.DynamicString(result.message)
                    )
                }
            }
        }
    }

    private fun updateUserAvatar(newAvatar: AvatarType) {
        val currentUser = _state.value.userInfo ?: return
        val updatedUser = currentUser.copy(avatar = newAvatar)

        viewModelScope.launch {
            updateUserInfoUseCase(updatedUser.toDomain()).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> _state.update { it.copy(userInfo = updatedUser) }
                    is Resource.Error -> SnackbarManager.showMessage(
                        UiText.DynamicString(result.message)
                    )
                }
            }
        }
    }

    private fun navigateToLogin() {
        viewModelScope.launch {
            clearRememberMeUseCase()
            _event.emit(ProfileUiEvents.NavigateToLogin)
        }
    }

    sealed class ProfileUiEvents {
        data object NavigateToLogin : ProfileUiEvents()
    }
}
