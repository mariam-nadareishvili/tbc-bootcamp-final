package com.example.android_bootcamp.presentation.screen.details

sealed class BookDetailsEvent {
    data class SaveBookId(val bookId: String): BookDetailsEvent()
    data class OpenBook(val url: String): BookDetailsEvent()
    data class OpenSimilarBook(val bookId: String): BookDetailsEvent()
}