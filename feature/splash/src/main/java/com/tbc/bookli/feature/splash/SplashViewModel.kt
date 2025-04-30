package com.tbc.bookli.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbc.bookli.core.domain.usecase.GetIntroSeenUseCase
import com.tbc.bookli.core.domain.usecase.GetRememberMeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getIntroSeenUseCase: GetIntroSeenUseCase,
    private val getRememberMeUseCase: GetRememberMeUseCase
) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<SplashUiEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    init {
        checkDestination()
    }

    private fun checkDestination() {
        viewModelScope.launch {
            val introSeen = getIntroSeenUseCase().first()
            if (!introSeen) {
                _navigationEvent.emit(SplashUiEvent.NavigateToIntro)
                return@launch
            }

            val rememberMe = getRememberMeUseCase().first()
            if (rememberMe) {
                _navigationEvent.emit(SplashUiEvent.NavigateToHome)
            } else {
                _navigationEvent.emit(SplashUiEvent.NavigateToLogin)
            }
        }
    }

    sealed interface SplashUiEvent {
        data object NavigateToIntro : SplashUiEvent
        data object NavigateToLogin : SplashUiEvent
        data object NavigateToHome : SplashUiEvent
    }
}
