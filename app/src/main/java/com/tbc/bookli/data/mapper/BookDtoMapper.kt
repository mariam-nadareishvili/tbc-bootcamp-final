package com.tbc.bookli.data.mapper

import com.tbc.bookli.data.remote.model.BookDto
import com.tbc.bookli.data.remote.model.RatingInfoDto
import com.tbc.bookli.data.remote.model.ReviewDto
import com.tbc.bookli.domain.model.Book
import com.tbc.bookli.domain.model.BookStatus
import com.tbc.bookli.domain.model.Review

fun BookDto.toDomain(): Book {
    return Book(
        id = id,
        imageUrl = imageUrl,
        title = title,
        author = author,
        aboutAuthor = aboutAuthor,
        aboutBook = aboutBook,
        genres = genres,
        source = source,
        readBy = readBy,
        pages = pages,
        status = BookStatus.from(status),
        rating = ratingInfo.rating,
        votes = ratingInfo.votes,
        reviews = ratingInfo.reviews.map { it.toDomain() }
    )
}

fun ReviewDto.toDomain(): Review {
    return Review(
        reviewerName = reviewerName,
        date = date,
        comment = comment,
        rating = rating,
        avatar = avatar
    )
}

fun Book.toDto(): BookDto {
    return BookDto(
        id = id,
        imageUrl = imageUrl,
        title = title,
        author = author,
        aboutAuthor = aboutAuthor,
        aboutBook = aboutBook,
        genres = genres,
        source = source,
        readBy = readBy,
        pages = pages,
        status = status?.value,
        ratingInfo = RatingInfoDto(
            rating = rating,
            votes = votes,
            reviews = reviews.map { it.toDto() }
        )
    )
}

fun Review.toDto(): ReviewDto {
    return ReviewDto(
        reviewerName = reviewerName,
        date = date,
        comment = comment,
        rating = rating,
        avatar = avatar
    )
}