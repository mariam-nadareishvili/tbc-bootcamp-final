package com.tbc.bookli.feature.details

import com.tbc.bookli.core.domain.model.BookStatus

sealed class BookDetailsEvent {
    data class LoadBookDetails(val id: String) : BookDetailsEvent()
    data class NavigateToRead(val url: String) : BookDetailsEvent()
    data class NavigateToBookDetails(val id: String) : BookDetailsEvent()
    data class UpdateBookStatus(val status: BookStatus?) : BookDetailsEvent()
    data object NavigateToReview : BookDetailsEvent()
    data object OnBackPress : BookDetailsEvent()
}