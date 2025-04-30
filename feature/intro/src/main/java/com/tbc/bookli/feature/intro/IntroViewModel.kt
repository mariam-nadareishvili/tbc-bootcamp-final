package com.tbc.bookli.feature.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbc.bookli.core.domain.usecase.GetIntroSeenUseCase
import com.tbc.bookli.core.domain.usecase.SaveIntroSeenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val saveIntroSeenUseCase: SaveIntroSeenUseCase,
    private val getIntroSeenUseCase: GetIntroSeenUseCase
) : ViewModel() {

    private val _eventFlow = MutableSharedFlow<IntroUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun markIntroAsSeen() {
        viewModelScope.launch {
            saveIntroSeenUseCase(true)

            getIntroSeenUseCase().collectLatest { seen ->
                if (seen) {
                    _eventFlow.emit(IntroUiEvent.NavigateToNext)
                    return@collectLatest
                }
            }
        }
    }

    sealed class IntroUiEvent {
        data object NavigateToNext : IntroUiEvent()
    }
}