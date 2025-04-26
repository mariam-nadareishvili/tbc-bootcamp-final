package com.example.android_bootcamp.presentation.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.android_bootcamp.common.Resource
import com.example.android_bootcamp.domain.useCase.GetFeedBooksUseCase
import com.example.android_bootcamp.domain.useCase.GetStoriesUseCase
import com.example.android_bootcamp.presentation.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
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

    private val _homeState = MutableStateFlow(HomeUiState())
    val homeState get() = _homeState.asStateFlow()

    private val _homeUiEvents = MutableSharedFlow<HomeUiEvents>()
    val homeUiEvents: SharedFlow<HomeUiEvents> = _homeUiEvents

    init {
        fetchBooks()
        getStories()
    }

    private fun fetchBooks() {
        viewModelScope.launch(Dispatchers.IO) {
            getFeedBooksUseCase()
                .cachedIn(viewModelScope)
                .onStart {
                    _homeState.update {
                        it.copy(isLoading = true)
                    }
                }
                .catch { result ->
                    _homeState.update { it.copy(isLoading = false) }
                    _homeUiEvents.emit(
                        HomeUiEvents.ShowError(
                            message = result.message ?: "General error"
                        )
                    )
                }
                .collect { pagingData ->
                    _homeState.update {
                        it.copy(feedBookList = flowOf(pagingData.map { it.toPresentation() }))
                    }
                }
        }
    }

    private fun getStories() {
        viewModelScope.launch(Dispatchers.IO) {
            getStoriesUseCase()
                .collectLatest { result ->
                    when (result) {
                        is Resource.Loading -> _homeState.update { it.copy(isLoading = result.isLoading) }
                        is Resource.Success -> _homeState.update {
                            it.copy(
                                storyList = result.data?.map { it.toPresentation() } ?: emptyList()
                            )
                        }

                        is Resource.Error -> _homeUiEvents.emit(HomeUiEvents.ShowError(message = result.message))
                    }
                }
        }
    }

    fun navigateToBookDetails(bookId: String) {
        viewModelScope.launch {
            _homeUiEvents.emit(HomeUiEvents.NavigateToBookDetails(id = bookId))
        }
    }
}