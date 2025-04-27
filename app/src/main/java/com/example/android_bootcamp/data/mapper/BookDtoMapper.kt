package com.example.android_bootcamp.data.mapper

import com.example.android_bootcamp.data.remote.model.BookDto
import com.example.android_bootcamp.domain.model.Book

fun BookDto.toDomain(): Book {
    return Book(
        id = id,
        imageUrl = imageUrl,
        title = title,
        rating = rating,
        averagePrice = averagePrice,
        aboutBook = aboutBook,
        author = author,
        aboutAuthor = aboutAuthor,
        genres = genres,
        source = source
    )
}