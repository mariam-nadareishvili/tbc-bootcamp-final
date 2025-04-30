package com.tbc.bookli.core.domain.usecase

import com.tbc.bookli.core.domain.Resource
import com.tbc.bookli.core.domain.mapResource
import com.tbc.bookli.core.domain.model.BookStatus
import com.tbc.bookli.core.domain.model.FavoriteBooks
import com.tbc.bookli.core.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FilterFavoriteBooksUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    operator fun invoke(): Flow<Resource<FavoriteBooks>> {
        return bookRepository.getAllBooks()
            .mapResource { books ->
                val groupedBooks = books
                    .filter { it.status == BookStatus.Favorites || it.status == BookStatus.Reading }
                    .groupBy { it.status }

                FavoriteBooks(
                    favorites = groupedBooks[BookStatus.Favorites] ?: emptyList(),
                    readings = groupedBooks[BookStatus.Reading] ?: emptyList()
                )
            }
    }
}


