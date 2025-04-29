package com.tbc.bookli.presentation.screen.review

import com.tbc.bookli.presentation.screen.profile.UserUi
import com.tbc.bookli.presentation.screen.search.BookUi

data class ReviewUiState(
    val isLoading: Boolean = false,
    val bookUi: BookUi? = null,
    val rating: Int = 0,
    val review: String = "",
    val userId: String = "ae9aff30-a102-4e36-bfc3-70d0a87efde9", // Hardcoded user id due to api restrictions
    val userInfo: UserUi? = null
)