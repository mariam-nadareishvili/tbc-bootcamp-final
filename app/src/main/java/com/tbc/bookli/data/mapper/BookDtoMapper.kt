package com.tbc.bookli.data.mapper

import com.tbc.bookli.data.remote.model.BookDto
import com.tbc.bookli.domain.model.Book

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