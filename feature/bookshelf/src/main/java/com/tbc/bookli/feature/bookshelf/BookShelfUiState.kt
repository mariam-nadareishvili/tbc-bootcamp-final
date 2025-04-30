package com.tbc.bookli.feature.bookshelf

import com.tbc.bookli.core.ui.model.BookUi

data class BookShelfUiState(
    val isLoading: Boolean = false,
    val favoriteBooks: List<BookUi> = emptyList(),
    val readingBooks: List<BookUi> = emptyList()
)
