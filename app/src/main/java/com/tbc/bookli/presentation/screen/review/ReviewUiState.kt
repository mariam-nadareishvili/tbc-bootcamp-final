package com.tbc.bookli.presentation.screen.review

import com.tbc.bookli.presentation.screen.search.BookUi

data class ReviewUiState(
    val isLoading: Boolean = false,
    val bookUi: BookUi? = null,
    val rating: Int = 0,
    val review: String = ""
)