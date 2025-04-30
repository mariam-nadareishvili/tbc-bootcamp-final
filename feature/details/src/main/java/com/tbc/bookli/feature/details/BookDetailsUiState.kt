package com.tbc.bookli.feature.details

import com.tbc.bookli.core.ui.model.BookUi

data class BookDetailsUiState(
    val isLoading: Boolean = false,
    val bookDetails: BookUi? = null,
    val similarBooks: List<BookUi>? = emptyList(),
)