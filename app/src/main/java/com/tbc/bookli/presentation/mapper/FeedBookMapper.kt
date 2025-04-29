package com.tbc.bookli.presentation.mapper

import com.tbc.bookli.domain.model.FeedBook
import com.tbc.bookli.presentation.screen.home.FeedBookUi

fun FeedBook.toPresentation(): FeedBookUi {
    return FeedBookUi(
        id = id,
        imageUrl = imageUrl,
        title = title,
        rating = rating
    )
}