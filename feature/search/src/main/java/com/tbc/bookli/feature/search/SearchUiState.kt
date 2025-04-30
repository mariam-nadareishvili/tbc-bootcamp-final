package com.tbc.bookli.feature.search

import com.tbc.bookli.core.ui.model.BookUi
import com.tbc.bookli.core.ui.model.GenreUi

data class SearchUiState(
    val isLoading: Boolean = false,
    val genres: List<GenreUi> = emptyList(),
    val searchBook: List<BookUi> = emptyList(),
    val searchQuery: String = "",
    val selectedGenre: String? = null
)