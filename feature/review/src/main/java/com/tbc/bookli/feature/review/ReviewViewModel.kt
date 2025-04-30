package com.tbc.bookli.feature.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tbc.bookli.core.common.helper.DateHelper.getCurrentDateFormatted
import com.tbc.bookli.core.ui.SnackbarManager
import com.tbc.bookli.core.ui.UiText
import com.tbc.bookli.core.domain.Resource
import com.tbc.bookli.core.domain.usecase.GetUserUserInfoCase
import com.tbc.bookli.core.domain.usecase.UpdateBookByIdUseCase
import com.tbc.bookli.core.ui.mapper.toDomain
import com.tbc.bookli.core.ui.mapper.toPresentation
import com.tbc.bookli.core.ui.model.AvatarType
import com.tbc.bookli.core.ui.model.ReviewUi
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
    private val updateBookByIdUseCase: UpdateBookByIdUseCase,
    private val getUserInfoUseCase: GetUserUserInfoCase,
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
                fetchUserInfo()
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
                reviewerName = _state.value.userInfo?.fullName ?: "",
                date = getCurrentDateFormatted(),
                comment = currentState.review,
                rating = currentState.rating.toDouble(),
                avatar = _state.value.userInfo?.avatar?.key ?: AvatarType.RABBIT.key
            )

            val updatedBook = currentBook.copy(
                reviews = listOf(newReview) + currentBook.reviews
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
                        SnackbarManager.showMessage(UiText.DynamicString(result.message))
                    }
                }
            }
        }
    }

    private fun fetchUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            getUserInfoUseCase(_state.value.userId).collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = result.isLoading) }
                    is Resource.Success -> _state.update { it.copy(userInfo = result.data?.toPresentation()) }
                    is Resource.Error -> SnackbarManager.showMessage(UiText.DynamicString(result.message))
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
        data object OnBackPress : ReviewUiEvent()
    }
}