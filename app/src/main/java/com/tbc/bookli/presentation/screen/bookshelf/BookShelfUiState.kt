package com.tbc.bookli.presentation.screen.bookshelf

import com.tbc.bookli.presentation.screen.search.BookUi

data class BookShelfUiState(
    val isLoading: Boolean = false,
    val favoriteBooks: List<BookUi> = emptyList(),
    val readingBooks: List<BookUi> = emptyList()
)
