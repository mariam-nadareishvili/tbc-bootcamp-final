package com.tbc.bookli.feature.review

import com.tbc.bookli.core.ui.model.BookUi

sealed class ReviewScreenEvent {
    data object BackPress : ReviewScreenEvent()
    data class SaveBook(val book: BookUi) : ReviewScreenEvent()
    data class UpdateRating(val rating: Int) : ReviewScreenEvent()
    data class UpdateReview(val review: String) : ReviewScreenEvent()
    data object Submit : ReviewScreenEvent()
}