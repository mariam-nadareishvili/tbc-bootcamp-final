package com.tbc.bookli.feature.review

import com.tbc.bookli.core.ui.model.UserUi
import com.tbc.bookli.core.ui.model.BookUi

data class ReviewUiState(
    val isLoading: Boolean = false,
    val bookUi: BookUi? = null,
    val rating: Int = 0,
    val review: String = "",
    val userId: String = "ae9aff30-a102-4e36-bfc3-70d0a87efde9", // Hardcoded user id due to api restrictions
    val userInfo: UserUi? = null
)