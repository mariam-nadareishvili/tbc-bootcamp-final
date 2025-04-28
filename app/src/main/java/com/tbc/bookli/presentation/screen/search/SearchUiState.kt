package com.tbc.bookli.presentation.screen.search

data class SearchUiState(
    val isLoading: Boolean = false,
    val genres: List<GenreUi> = emptyList(),
    val searchBook: List<BookUi> = emptyList(),
    val searchQuery: String = "",
    val selectedGenre: String? = null
)