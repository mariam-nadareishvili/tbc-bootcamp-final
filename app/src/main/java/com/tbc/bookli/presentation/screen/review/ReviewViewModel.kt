package com.tbc.bookli.presentation.screen.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbc.bookli.presentation.screen.details.BookDetailsUiState
import com.tbc.bookli.presentation.screen.details.BookDetailsViewModel.BookDetailsUiEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReviewViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(ReviewUiState())
    val state get() = _state.asStateFlow()


    private val _uiEvents = MutableSharedFlow<ReviewUiEvent>()
    val uiEvents: SharedFlow<ReviewUiEvent> = _uiEvents

    fun navigateBack() {
        viewModelScope.launch {
            _uiEvents.emit(ReviewUiEvent.OnBackPress)
        }
    }

    sealed class ReviewUiEvent {
        data class ShowError(val message: String) : ReviewUiEvent()
        data object OnBackPress : ReviewUiEvent()
    }
}