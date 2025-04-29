package com.tbc.bookli.presentation.screen.bookshelf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbc.bookli.presentation.screen.details.BookDetailsUiState
import com.tbc.bookli.presentation.screen.details.BookDetailsViewModel.BookDetailsUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookShelfViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(BookShelfUiState())
    val state get() = _state.asStateFlow()


    private val _uiEvents = MutableSharedFlow<BookShelfUiEvent>()
    val uiEvents: SharedFlow<BookShelfUiEvent> = _uiEvents


    fun navigateToSavedBookScreen() {
        viewModelScope.launch {
            _uiEvents.emit(BookShelfUiEvent.NavigateToSavedBookScreen)
        }
    }

    sealed class BookShelfUiEvent {
        data class ShowError(val message: String) : BookShelfUiEvent()
        data object NavigateToSavedBookScreen : BookShelfUiEvent()
        data object OnBackPress : BookShelfUiEvent()
    }
}