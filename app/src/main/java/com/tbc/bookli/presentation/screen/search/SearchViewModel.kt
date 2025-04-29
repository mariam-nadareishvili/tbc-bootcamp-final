package com.tbc.bookli.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbc.bookli.common.Resource
import com.tbc.bookli.domain.useCase.GetGenresUseCase
import com.tbc.bookli.domain.useCase.GetSearchBooksUseCase
import com.tbc.bookli.presentation.helper.SnackbarManager
import com.tbc.bookli.presentation.helper.UiText
import com.tbc.bookli.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchBooksUseCase: GetSearchBooksUseCase,
    private val getGenresUseCase: GetGenresUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SearchUiState())
    val state: StateFlow<SearchUiState> = _state.asStateFlow()

    private val _uiEvents = MutableSharedFlow<SearchUiEvent>()
    val uiEvents: SharedFlow<SearchUiEvent> = _uiEvents.asSharedFlow()

    private var searchJob: Job? = null

    init {
        loadGenres()
    }

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.QueryChanged -> handleQuery(event.query)
            is SearchEvent.GenreSelected -> searchByGenre(event.genre)
            is SearchEvent.BookClicked -> navigateToBookDetails(event.id)
        }
    }

    private fun loadGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            getGenresUseCase().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> _state.update {
                        it.copy(genres = result.data?.map { genre -> genre.toPresentation() }
                            ?: emptyList())
                    }

                    is Resource.Error -> SnackbarManager.showMessage(UiText.DynamicString(result.message))

                }
            }
        }
    }

    private fun handleQuery(query: String) {
        _state.update { it.copy(searchQuery = query, selectedGenre = null) }
        searchJob?.cancel()

        if (query.length >= 3) {
            searchJob = viewModelScope.launch(Dispatchers.IO) {
                delay(500)
                searchBooksUseCase(query).collectLatest { result ->
                    when (result) {
                        is Resource.Loading -> _state.update { it.copy(isLoading = result.isLoading) }
                        is Resource.Success -> _state.update {
                            it.copy(searchBook = result.data?.map { it.toPresentation() }
                                ?: emptyList())
                        }

                        is Resource.Error -> {
                            _state.update { it.copy(searchBook = emptyList()) }
                            SnackbarManager.showMessage(UiText.DynamicString(result.message))
                        }
                    }
                }
            }
        } else {
            _state.update { it.copy(searchBook = emptyList()) }
        }
    }

    private fun searchByGenre(genre: String) {
        _state.update { it.copy(selectedGenre = genre) }
        searchJob?.cancel()

        searchJob = viewModelScope.launch(Dispatchers.IO) {
            searchBooksUseCase(genre).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> _state.update {
                        it.copy(searchBook = result.data?.map { it.toPresentation() }
                            ?: emptyList())
                    }

                    is Resource.Error -> {
                        _state.update { it.copy(searchBook = emptyList()) }
                        SnackbarManager.showMessage(UiText.DynamicString(result.message))
                    }
                }
            }
        }
    }

    private fun navigateToBookDetails(id: String) {
        viewModelScope.launch {
            _uiEvents.emit(SearchUiEvent.NavigateToBookDetail(id))
        }
    }

    sealed class SearchUiEvent {
        data class NavigateToBookDetail(val id: String) : SearchUiEvent()
    }
}