package com.tbc.bookli.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.tbc.bookli.common.Resource
import com.tbc.bookli.domain.useCase.GetFeedBooksUseCase
import com.tbc.bookli.domain.useCase.GetStoriesUseCase
import com.tbc.bookli.presentation.helper.SnackbarManager
import com.tbc.bookli.presentation.helper.UiText
import com.tbc.bookli.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getFeedBooksUseCase: GetFeedBooksUseCase,
    private val getStoriesUseCase: GetStoriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    private val _uiEvent = MutableSharedFlow<HomeUiEvent>()
    val uiEvent: SharedFlow<HomeUiEvent> = _uiEvent.asSharedFlow()

    init {
        onEvent(HomeEvent.LoadFeedBooks)
        onEvent(HomeEvent.LoadStories)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadFeedBooks -> loadFeedBooks()
            is HomeEvent.LoadStories -> loadStories()
            is HomeEvent.BookClicked -> navigateToBookDetails(event.id)
        }
    }

    private fun loadFeedBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            getFeedBooksUseCase()
                .cachedIn(viewModelScope)
                .onStart {
                    _state.update { it.copy(isLoading = true) }
                }
                .catch { e ->
                    _state.update { it.copy(isLoading = false) }
                    SnackbarManager.showMessage(
                        UiText.DynamicString(e.message ?: "Something went wrong")
                    )
                }
                .collect { pagingData ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            feedBookList = flowOf(pagingData.map { it.toPresentation() })
                        )
                    }
                }
        }
    }

    private fun loadStories() {
        viewModelScope.launch(Dispatchers.IO) {
            getStoriesUseCase()
                .collectLatest { result ->
                    when (result) {
                        is Resource.Loading -> _state.update { it.copy(isLoading = result.isLoading) }

                        is Resource.Success -> _state.update {
                            it.copy(
                                storyList = result.data?.map { it.toPresentation() } ?: emptyList()
                            )
                        }

                        is Resource.Error -> SnackbarManager.showMessage(
                            UiText.DynamicString(result.message)
                        )
                    }
                }
        }
    }

    private fun navigateToBookDetails(bookId: String) {
        viewModelScope.launch {
            _uiEvent.emit(HomeUiEvent.NavigateToBookDetails(bookId))
        }
    }

    sealed class HomeUiEvent {
        data class NavigateToBookDetails(val id: String) : HomeUiEvent()
    }
}