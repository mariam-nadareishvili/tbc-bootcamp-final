package com.tbc.bookli

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbc.bookli.domain.useCase.GetAppLanguageUseCase
import com.tbc.bookli.domain.useCase.GetDarkModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val getDarkModeUseCase: GetDarkModeUseCase,
    private val getCurrentLanguage: GetAppLanguageUseCase
) : ViewModel() {

    private val _isDarkModeEnabled = MutableStateFlow(false)
    val isDarkModeEnabled: StateFlow<Boolean> = _isDarkModeEnabled

    private val _currentLanguage = MutableStateFlow("en")
    val currentLanguage: StateFlow<String> = _currentLanguage

    init {
        viewModelScope.launch {
            getDarkModeUseCase().collect { isDarkMode ->
                _isDarkModeEnabled.value = isDarkMode
            }
        }

        viewModelScope.launch {
            getCurrentLanguage().collect { language ->
                _currentLanguage.value = language
                println("language: $language")
            }
        }
    }
}
