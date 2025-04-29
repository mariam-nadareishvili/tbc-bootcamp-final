package com.tbc.bookli.presentation.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbc.bookli.common.Resource
import com.tbc.bookli.domain.useCase.GetBookByIdUseCase
import com.tbc.bookli.domain.useCase.GetSearchBooksUseCase
import com.tbc.bookli.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(
    private val getBookByIdUseCase: GetBookByIdUseCase,
    private val searchBooksUseCase: GetSearchBooksUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BookDetailsUiState())
    val state get() = _state.asStateFlow()


    private val _uiEvents = MutableSharedFlow<BookDetailsUiEvent>()
    val uiEvents: SharedFlow<BookDetailsUiEvent> = _uiEvents

    fun getBookDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getBookByIdUseCase(id).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _state.update {
                        it.copy(isLoading = result.isLoading)
                    }

                    is Resource.Success -> {
                        val bookUi = result.data?.toPresentation()

                        _state.update {
                            it.copy(bookDetails = bookUi)
                        }

                        bookUi?.let {
                            getSimilarBooks(bookUi.genres.first().name)
                        }
                    }

                    is Resource.Error -> _uiEvents.emit(BookDetailsUiEvent.ShowError(message = result.message))
                }
            }
        }
    }

    private fun getSimilarBooks(genre: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchBooksUseCase(searchQuery = genre).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> _state.update { it.copy(similarBooks = result.data?.map { it.toPresentation() }) }
                    is Resource.Error -> _uiEvents.emit(BookDetailsUiEvent.ShowError(message = result.message))
                }
            }
        }
    }

    fun navigateToReadScreen(url: String?) {
        url?.let {
            viewModelScope.launch {
                _uiEvents.emit(BookDetailsUiEvent.NavigateToReadScreen(url = url))
            }
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            _uiEvents.emit(BookDetailsUiEvent.OnBackPress)
        }
    }

    fun navigateToBookDetails(id: String) {
        viewModelScope.launch {
            _uiEvents.emit(BookDetailsUiEvent.NavigateToBookDetails(id = id))
        }
    }

    fun navigateToReviewScreen() {
        viewModelScope.launch {
            _uiEvents.emit(BookDetailsUiEvent.NavigateToReviewScreen)
        }
    }

    sealed class BookDetailsUiEvent {
        data class ShowError(val message: String) : BookDetailsUiEvent()
        data class NavigateToBookDetails(val id: String) : BookDetailsUiEvent()
        data class NavigateToReadScreen(val url: String) : BookDetailsUiEvent()
        data object OnBackPress : BookDetailsUiEvent()
        data object NavigateToReviewScreen : BookDetailsUiEvent()
    }
}
