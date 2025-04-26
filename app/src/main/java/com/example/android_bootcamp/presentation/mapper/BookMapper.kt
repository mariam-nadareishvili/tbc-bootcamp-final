package com.example.android_bootcamp.presentation.mapper

import com.example.android_bootcamp.data.remote.model.BookDto
import com.example.android_bootcamp.domain.model.Book
import com.example.android_bootcamp.presentation.screen.search.BookUi
import com.example.android_bootcamp.presentation.screen.search.GenreUi
import java.util.UUID

fun Book.toPresentation(): BookUi {
    return BookUi(
        id = id,
        imageUrl = imageUrl,
        title = title,
        rating = rating,
        averagePrice = averagePrice,
        aboutBook = aboutBook,
        author = author,
        aboutAuthor = aboutAuthor,
        genres = genres.split(",").map {
            GenreUi(id = UUID.randomUUID().toString(), name = it)
        },
        source = source
    )
}