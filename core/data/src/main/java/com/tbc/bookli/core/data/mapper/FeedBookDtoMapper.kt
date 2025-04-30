package com.tbc.bookli.core.data.mapper

import com.tbc.bookli.core.data.remote.model.FeedBookDto
import com.tbc.bookli.core.domain.model.FeedBook

fun FeedBookDto.toDomain(): FeedBook {
    return FeedBook(
        id = id,
        imageUrl = imageUrl,
        title = title,
        rating = rating
    )
}