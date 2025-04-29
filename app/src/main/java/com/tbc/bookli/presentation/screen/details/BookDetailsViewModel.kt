package com.tbc.bookli.presentation.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbc.bookli.common.Resource
import com.tbc.bookli.domain.useCase.GetBookByIdUseCase
import com.tbc.bookli.domain.useCase.GetSearchBooksUseCase
import com.tbc.bookli.presentation.mapper.toPresentation
import com.tbc.bookli.presentation.screen.search.BookUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    private val getBookById: GetBookByIdUseCase,
    private val searchBooks: GetSearchBooksUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BookDetailsUiState())
    val state: StateFlow<BookDetailsUiState> = _state

    private val _uiEvents = MutableSharedFlow<BookDetailsUiEvent>()
    val uiEvents: SharedFlow<BookDetailsUiEvent> = _uiEvents

    fun onEvent(event: BookDetailsEvent) {
        when (event) {
            is BookDetailsEvent.LoadBookDetails -> loadBook(event.id)
            is BookDetailsEvent.NavigateToRead -> emitUiEvent(BookDetailsUiEvent.NavigateToReadScreen(event.url))
            is BookDetailsEvent.NavigateToBookDetails -> emitUiEvent(BookDetailsUiEvent.NavigateToBookDetails(event.id))
            BookDetailsEvent.NavigateToReview -> navigateToReview()
            BookDetailsEvent.OnBackPress -> emitUiEvent(BookDetailsUiEvent.OnBackPress)
        }
    }

    private fun loadBook(id: String) = viewModelScope.launch(Dispatchers.IO) {
        getBookById(id).collectLatest { result ->
            when (result) {
                is Resource.Loading -> _state.update { it.copy(isLoading = result.isLoading) }
                is Resource.Success -> {
                    val bookUi = result.data?.toPresentation()
                    _state.update { it.copy(bookDetails = bookUi) }
                    bookUi?.let { getSimilarBooks(it.genres.firstOrNull()?.name.orEmpty()) }
                }
                is Resource.Error -> emitUiEvent(BookDetailsUiEvent.ShowError(result.message))
            }
        }
    }

    private fun getSimilarBooks(genre: String) = viewModelScope.launch(Dispatchers.IO) {
        searchBooks(genre).collectLatest { result ->
            when (result) {
                is Resource.Loading -> _state.update { it.copy(isLoading = result.isLoading) }
                is Resource.Success -> _state.update { it.copy(similarBooks = result.data?.map { it.toPresentation() }) }
                is Resource.Error -> emitUiEvent(BookDetailsUiEvent.ShowError(result.message))
            }
        }
    }

    private fun navigateToReview() {
        _state.value.bookDetails?.let {
            emitUiEvent(BookDetailsUiEvent.NavigateToReviewScreen(it))
        }
    }

    private fun emitUiEvent(event: BookDetailsUiEvent) = viewModelScope.launch {
        _uiEvents.emit(event)
    }

    sealed class BookDetailsUiEvent {
        data class ShowError(val message: String) : BookDetailsUiEvent()
        data class NavigateToBookDetails(val id: String) : BookDetailsUiEvent()
        data class NavigateToReadScreen(val url: String) : BookDetailsUiEvent()
        data object OnBackPress : BookDetailsUiEvent()
        data class NavigateToReviewScreen(val bookUi: BookUi) : BookDetailsUiEvent()
    }
}

