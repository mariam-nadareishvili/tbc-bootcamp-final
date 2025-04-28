package com.tbc.bookli.data.mapper

import com.tbc.bookli.data.remote.model.FeedBookDto
import com.tbc.bookli.domain.model.FeedBook

fun FeedBookDto.toDomain(): FeedBook {
    return FeedBook(
        id = id,
        imageUrl = imageUrl,
        title = title,
        rating = rating,
        averagePrice = averagePrice
    )
}