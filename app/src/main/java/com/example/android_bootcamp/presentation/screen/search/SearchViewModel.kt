package com.example.android_bootcamp.presentation.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_bootcamp.common.Resource
import com.example.android_bootcamp.domain.useCase.GetGenresUseCase
import com.example.android_bootcamp.domain.useCase.GetSearchBooksUseCase
import com.example.android_bootcamp.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchBooksUseCase: GetSearchBooksUseCase,
    private val getGenresUseCase: GetGenresUseCase
) :
    ViewModel() {

    private val _state =
        MutableStateFlow(SearchUiState())
    val state get() = _state.asStateFlow()

    private val _uiEvents = MutableSharedFlow<SearchUiEvent>()
    val uiEvents: SharedFlow<SearchUiEvent> = _uiEvents

    private var searchJob: Job? = null

    init {
        getGenres()
    }

    private fun getGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            getGenresUseCase().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _state.update {
                        it.copy(
                            isLoading = result.isLoading
                        )
                    }

                    is Resource.Success -> _state.update {
                        it.copy(genres = result.data?.map { it.toPresentation() } ?: emptyList())
                    }

                    is Resource.Error -> _uiEvents.emit(SearchUiEvent.ShowError(message = result.message))
                }
            }
        }
    }

    fun search(query: String) {
        _state.update { it.copy(searchQuery = query, selectedGenre = null) }

        searchJob?.cancel() // Cancel the previous job if it's still running

        if (query.length >= 3) {
            searchJob = viewModelScope.launch(Dispatchers.IO) {
                delay(500)

                searchBooksUseCase(searchQuery = query).collectLatest { result ->
                    when (result) {
                        is Resource.Loading -> _state.update {
                            it.copy(
                                isLoading = result.isLoading
                            )
                        }

                        is Resource.Success -> _state.update {
                            it.copy(searchBook = result.data?.map { it.toPresentation() }
                                ?: emptyList())
                        }

                        is Resource.Error -> {
                            _state.update { it.copy(searchBook = emptyList()) }
                            _uiEvents.emit(SearchUiEvent.ShowError(message = result.message))
                        }
                    }
                }
            }
        } else {
            _state.update { it.copy(searchBook = emptyList()) }
        }
    }

    fun searchByGenre(genre: String) {
        _state.update { it.copy(selectedGenre = genre) }
        searchJob?.cancel()

        searchJob = viewModelScope.launch(Dispatchers.IO) {
            searchBooksUseCase(searchQuery = genre).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _state.update {
                        it.copy(
                            isLoading = result.isLoading
                        )
                    }

                    is Resource.Success -> _state.update {
                        it.copy(searchBook = result.data?.map { it.toPresentation() }
                            ?: emptyList())
                    }

                    is Resource.Error -> {
                        _state.update { it.copy(searchBook = emptyList()) }
                        _uiEvents.emit(SearchUiEvent.ShowError(message = result.message))
                    }
                }
            }
        }
    }

    fun navigateToBookDetails(id: String) {
        viewModelScope.launch {
            _uiEvents.emit(SearchUiEvent.NavigateToBookDetail(id = id))
        }
    }


    sealed class SearchUiEvent {
        data class NavigateToBookDetail(val id: String) : SearchUiEvent()
        data class ShowError(val message: String) : SearchUiEvent()
    }
}