package com.tbc.bookli.presentation.screen.details

import com.tbc.bookli.presentation.screen.search.BookUi

data class BookDetailsUiState(
    val isLoading: Boolean = false,
    val bookDetails: BookUi? = null,
    val similarBooks: List<BookUi>? = emptyList(),
)