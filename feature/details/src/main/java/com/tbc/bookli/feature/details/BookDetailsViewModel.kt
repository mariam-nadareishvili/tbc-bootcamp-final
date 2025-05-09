package com.tbc.bookli.feature.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbc.bookli.core.ui.SnackbarManager
import com.tbc.bookli.core.ui.UiText
import com.tbc.bookli.core.domain.Resource
import com.tbc.bookli.core.domain.usecase.GetBookByIdUseCase
import com.tbc.bookli.core.domain.usecase.GetSearchBooksUseCase
import com.tbc.bookli.core.domain.usecase.UpdateBookByIdUseCase
import com.tbc.bookli.core.ui.model.BookUi
import com.tbc.bookli.core.ui.mapper.toDomain
import com.tbc.bookli.core.ui.mapper.toPresentation
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
    private val searchBooks: GetSearchBooksUseCase,
    private val updateBookByIdUseCase: UpdateBookByIdUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(BookDetailsUiState())
    val state: StateFlow<BookDetailsUiState> = _state

    private val _uiEvents = MutableSharedFlow<BookDetailsUiEvent>()
    val uiEvents: SharedFlow<BookDetailsUiEvent> = _uiEvents

    fun onEvent(event: BookDetailsEvent) {
        when (event) {
            is BookDetailsEvent.LoadBookDetails -> loadBook(event.id)
            is BookDetailsEvent.NavigateToRead -> emitUiEvent(
                BookDetailsUiEvent.NavigateToReadScreen(
                    event.url
                )
            )

            is BookDetailsEvent.NavigateToBookDetails -> emitUiEvent(
                BookDetailsUiEvent.NavigateToBookDetails(
                    event.id
                )
            )

            BookDetailsEvent.NavigateToReview -> navigateToReview()
            BookDetailsEvent.OnBackPress -> emitUiEvent(BookDetailsUiEvent.OnBackPress)
            is BookDetailsEvent.UpdateBookStatus -> updateBookStatus(status = event.status)
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

                is Resource.Error -> SnackbarManager.showMessage(UiText.DynamicString(result.message))

            }
        }
    }

    private fun getSimilarBooks(genre: String) = viewModelScope.launch(Dispatchers.IO) {
        searchBooks(genre).collectLatest { result ->
            when (result) {
                is Resource.Loading -> _state.update { it.copy(isLoading = result.isLoading) }
                is Resource.Success -> _state.update { it.copy(similarBooks = result.data?.map { it.toPresentation() }) }
                is Resource.Error -> SnackbarManager.showMessage(UiText.DynamicString(result.message))
            }
        }
    }

    private fun updateBookStatus(status: com.tbc.bookli.core.domain.model.BookStatus?) {
        viewModelScope.launch(Dispatchers.IO) {
            val updatedBook = _state.value.bookDetails?.copy(status = status)
            updateBookByIdUseCase(book = updatedBook!!.toDomain()).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _state.update {
                        it.copy(isLoading = result.isLoading)
                    }

                    is Resource.Success -> {
                        _state.update { it.copy(bookDetails = updatedBook) }
                    }

                    is Resource.Error -> {
                        SnackbarManager.showMessage(UiText.DynamicString(result.message))
                    }
                }
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
        data class NavigateToBookDetails(val id: String) : BookDetailsUiEvent()
        data class NavigateToReadScreen(val url: String) : BookDetailsUiEvent()
        data object OnBackPress : BookDetailsUiEvent()
        data class NavigateToReviewScreen(val bookUi: BookUi) : BookDetailsUiEvent()
    }
}

