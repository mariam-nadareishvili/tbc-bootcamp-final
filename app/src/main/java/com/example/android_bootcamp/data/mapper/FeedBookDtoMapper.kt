package com.example.android_bootcamp.data.mapper

import com.example.android_bootcamp.data.remote.model.FeedBookDto
import com.example.android_bootcamp.domain.model.FeedBook

fun FeedBookDto.toDomain(): FeedBook {
    return FeedBook(
        id = id,
        imageUrl = imageUrl,
        title = title,
        rating = rating,
        averagePrice = averagePrice
    )
}