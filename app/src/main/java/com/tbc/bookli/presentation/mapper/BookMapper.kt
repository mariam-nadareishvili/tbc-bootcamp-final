package com.tbc.bookli.presentation.mapper

import com.tbc.bookli.domain.model.Book
import com.tbc.bookli.presentation.screen.search.BookUi
import com.tbc.bookli.presentation.screen.search.GenreUi
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