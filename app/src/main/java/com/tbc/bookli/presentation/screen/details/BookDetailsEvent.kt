package com.tbc.bookli.presentation.screen.details

sealed class BookDetailsEvent {
    data class LoadBookDetails(val id: String) : BookDetailsEvent()
    data class NavigateToRead(val url: String) : BookDetailsEvent()
    data class NavigateToBookDetails(val id: String) : BookDetailsEvent()
    data object NavigateToReview : BookDetailsEvent()
    data object OnBackPress : BookDetailsEvent()
}