package com.tbc.bookli.presentation.screen.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbc.bookli.common.Resource
import com.tbc.bookli.domain.useCase.UpdateBookByIdUseCase
import com.tbc.bookli.presentation.helper.DateHelper.getCurrentDateFormatted
import com.tbc.bookli.presentation.mapper.toDomain
import com.tbc.bookli.presentation.screen.search.ReviewUi
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
class ReviewViewModel @Inject constructor(
    private val updateBookByIdUseCase: UpdateBookByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ReviewUiState())
    val state get() = _state.asStateFlow()

    private val _uiEvents = MutableSharedFlow<ReviewUiEvent>()
    val uiEvents: SharedFlow<ReviewUiEvent> = _uiEvents

    fun onEvent(event: ReviewScreenEvent) {
        when (event) {
            is ReviewScreenEvent.SaveBook -> {
                _state.update {
                    it.copy(bookUi = event.book)
                }
            }

            is ReviewScreenEvent.UpdateRating -> {
                _state.update {
                    it.copy(rating = event.rating)
                }
            }

            is ReviewScreenEvent.UpdateReview -> {
                _state.update {
                    it.copy(review = event.review)
                }
            }

            ReviewScreenEvent.Submit -> {
                updateBookById()
            }

            ReviewScreenEvent.BackPress -> {
                navigateBack()
            }
        }
    }

    private fun updateBookById() {
        _state.update { currentState ->
            val currentBook = currentState.bookUi ?: return

            val newReview = ReviewUi(
                reviewerName = "Mariam Test", // TODO: Replace with actual user
                date = getCurrentDateFormatted(),
                comment = currentState.review,
                rating = currentState.rating.toDouble(),
                avatar = "cool" // TODO: Replace with actual avatar
            )

            val updatedBook = currentBook.copy(
                reviews = currentBook.reviews + newReview
            )

            currentState.copy(bookUi = updatedBook)
        }

        viewModelScope.launch(Dispatchers.IO) {
            updateBookByIdUseCase(book = _state.value.bookUi!!.toDomain()).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> _state.update {
                        it.copy(isLoading = result.isLoading)
                    }

                    is Resource.Success -> {
                        navigateBack()
                    }

                    is Resource.Error -> {
                        println("Resource.Error: ${result.message}")
                    }
                }
            }
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _uiEvents.emit(ReviewUiEvent.OnBackPress)
        }
    }

    sealed class ReviewUiEvent {
        data class ShowError(val message: String) : ReviewUiEvent()
        data object OnBackPress : ReviewUiEvent()
    }
}