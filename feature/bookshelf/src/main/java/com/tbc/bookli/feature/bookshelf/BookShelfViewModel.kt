package com.tbc.bookli.feature.bookshelf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbc.bookli.core.ui.SnackbarManager
import com.tbc.bookli.core.ui.UiText
import com.tbc.bookli.core.domain.Resource
import com.tbc.bookli.core.domain.model.BookStatus
import com.tbc.bookli.core.domain.usecase.FilterFavoriteBooksUseCase
import com.tbc.bookli.core.ui.mapper.toPresentation
import com.tbc.bookli.core.ui.model.BookUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookShelfViewModel @Inject constructor(
    private val filterFavoriteBooksUseCase: FilterFavoriteBooksUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(BookShelfUiState())
    val state get() = _state.asStateFlow()

    private val _uiEvents = MutableSharedFlow<BookShelfUiEvent>()
    val uiEvents: SharedFlow<BookShelfUiEvent> = _uiEvents

    fun onEvent(event: BookShelfEvent) {
        when (event) {
            is BookShelfEvent.LoadBooks -> getFavoriteBooks()
            is BookShelfEvent.NavigateToBookShelfDetails -> navigateToBookShelfDetails(event.status)
        }
    }

    private fun getFavoriteBooks() {
        viewModelScope.launch {
            filterFavoriteBooksUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.update { it.copy(isLoading = result.isLoading) }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                favoriteBooks = result.data?.favorites?.map { it.toPresentation() }
                                    ?: emptyList(),
                                readingBooks = result.data?.readings?.map { it.toPresentation() }
                                    ?: emptyList()
                            )
                        }
                    }

                    is Resource.Error -> {
                        SnackbarManager.showMessage(UiText.DynamicString(result.message))
                    }
                }
            }
        }
    }

    private fun navigateToBookShelfDetails(status: com.tbc.bookli.core.domain.model.BookStatus) {
        viewModelScope.launch {
            _uiEvents.emit(
                BookShelfUiEvent.NavigateToSavedBookScreen(
                    if (status == BookStatus.Favorites) {
                        _state.value.favoriteBooks
                    } else {
                        _state.value.readingBooks
                    }
                )
            )
        }
    }

    sealed class BookShelfUiEvent {
        data class NavigateToSavedBookScreen(val books: List<BookUi>) : BookShelfUiEvent()
    }
}