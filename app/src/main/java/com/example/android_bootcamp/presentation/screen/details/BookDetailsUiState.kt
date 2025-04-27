package com.example.android_bootcamp.presentation.screen.details

import com.example.android_bootcamp.presentation.screen.search.BookUi

data class BookDetailsUiState(
    val isLoading: Boolean = false,
    val bookDetails: BookUi? = null,
    val similarBooks: List<BookUi>? = emptyList()
)