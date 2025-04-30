package com.tbc.bookli.core.ui.mapper

import com.tbc.bookli.core.domain.model.Book
import com.tbc.bookli.core.domain.model.Review
import com.tbc.bookli.core.ui.model.BookUi
import com.tbc.bookli.core.ui.model.GenreUi
import com.tbc.bookli.core.ui.model.ReviewUi
import java.util.UUID

fun Book.toPresentation(): BookUi {
    return BookUi(
        id = id,
        imageUrl = imageUrl,
        title = title,
        author = author,
        aboutAuthor = aboutAuthor,
        aboutBook = aboutBook,
        genres = genres.split(",").map {
            GenreUi(id = UUID.randomUUID().toString(), name = it)
        },
        source = source,
        readBy = readBy,
        pages = pages,
        status = status,
        rating = rating,
        votes = votes,
        reviews = reviews.map { it.toPresentation() }
    )
}

fun Review.toPresentation(): ReviewUi {
    return ReviewUi(
        reviewerName = reviewerName,
        date = date,
        comment = comment,
        rating = rating,
        avatar = avatar
    )
}

fun BookUi.toDomain(): Book {
    return Book(
        id = id,
        imageUrl = imageUrl,
        title = title,
        author = author,
        aboutAuthor = aboutAuthor,
        aboutBook = aboutBook,
        genres = genres.joinToString(",") { it.name },
        source = source,
        readBy = readBy,
        pages = pages,
        status = status,
        rating = rating,
        votes = votes,
        reviews = reviews.map { it.toDomain() }
    )
}

fun ReviewUi.toDomain(): Review {
    return Review(
        reviewerName = reviewerName,
        date = date,
        comment = comment,
        rating = rating,
        avatar = avatar
    )
}

