package com.example.android_bootcamp.presentation.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.domain.useCase.ClearPreferencesUseCase
import com.example.android_bootcamp.domain.useCase.GetAppLanguageUseCase
import com.example.android_bootcamp.domain.useCase.GetDarkModeUseCase
import com.example.android_bootcamp.domain.useCase.SetDarkModeUseCase
import com.example.android_bootcamp.domain.useCase.UpdateLanguageUseCase
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
class ProfileViewModel @Inject constructor(
    private val updateLanguageUseCase: UpdateLanguageUseCase,
    private val getAppLanguageUseCase: GetAppLanguageUseCase,
    private val clearPreferencesUseCase: ClearPreferencesUseCase,
    private val getDarkModeUseCase: GetDarkModeUseCase,
    private val setDarkModeUseCase: SetDarkModeUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProfileUiState())
    val state get() = _state.asStateFlow()

    private val _event = MutableSharedFlow<ProfileUiEvents>()
    val events: SharedFlow<ProfileUiEvents> = _event

    //    fun fetchUserData() {
//        viewModelScope.launch {
//            _userData.value = Resource.Loading
//
//            val result = authRepository.getUserData()
//            if (result.isSuccess) {
//                val userDto = result.getOrNull()
//                if (userDto != null) {
//                    _userData.value = Resource.Success(userDto.toPresentation())
//                } else {
//                    _userData.value = Resource.Error("User data not found")
//                }
//            } else {
//                _userData.value =
//                    Resource.Error(result.exceptionOrNull()?.message ?: "Unknown error")
//            }
//        }
//    }
    init {
        getCurrentTheme()
        getCurrentLanguage()
    }
    // Get the current dark mode preference

    private fun getCurrentTheme() {
        viewModelScope.launch {
            getDarkModeUseCase().collect { isDarkMode ->
                _state.update { it.copy(isDarkMode = isDarkMode) }
            }
        }
    }

    // Update the dark mode preference when the switch is toggled

    fun onToggleTheme(isDarkMode: Boolean) {
        viewModelScope.launch {
            setDarkModeUseCase(isDarkMode)
            // Update UI state to reflect the new dark mode setting
            _state.update { it.copy(isDarkMode = isDarkMode) }
        }
    }

    private fun getCurrentLanguage() {
        viewModelScope.launch {
            getAppLanguageUseCase().collect { currentLanguage ->
                _state.update { it.copy(currentLanguage = currentLanguage) }
            }
        }
    }

    fun toggleLanguage() {
        val newLanguage =
            if (state.value.currentLanguage == LanguageType.GEORGIAN.language) {
                LanguageType.ENGLISH.language
            } else {
                LanguageType.GEORGIAN.language
            }

        viewModelScope.launch {
            updateLanguageUseCase(newLanguage)

            _state.update {
                it.copy(currentLanguage = newLanguage)
            }
        }
    }

    fun clear() {
        viewModelScope.launch(Dispatchers.IO) {
            clearPreferencesUseCase
        }
    }

    fun navigateToLogin() {
        viewModelScope.launch {
            _event.emit(ProfileUiEvents.NavigateToLogin)
        }
    }


    sealed class ProfileUiEvents {
        data object NavigateToLogin : ProfileUiEvents()
    }
}