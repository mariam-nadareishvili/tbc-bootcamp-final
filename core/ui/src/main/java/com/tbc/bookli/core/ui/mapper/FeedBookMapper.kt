package com.tbc.bookli.core.ui.mapper

import com.tbc.bookli.core.domain.model.FeedBook
import com.tbc.bookli.core.ui.model.FeedBookUi

fun FeedBook.toPresentation(): FeedBookUi {
    return FeedBookUi(
        id = id,
        imageUrl = imageUrl,
        title = title,
        rating = rating
    )
}