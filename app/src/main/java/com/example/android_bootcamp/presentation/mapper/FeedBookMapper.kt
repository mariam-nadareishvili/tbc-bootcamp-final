package com.example.android_bootcamp.presentation.mapper

import com.example.android_bootcamp.domain.model.FeedBook
import com.example.android_bootcamp.presentation.screen.home.FeedBookUi

fun FeedBook.toPresentation(): FeedBookUi {
    return FeedBookUi(
        id = id,
        imageUrl = imageUrl,
        title = title,
        rating = rating,
        averagePrice = averagePrice
    )
}