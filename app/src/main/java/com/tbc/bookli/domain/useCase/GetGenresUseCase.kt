package com.tbc.bookli.domain.useCase

import com.tbc.bookli.common.Resource
import com.tbc.bookli.domain.model.Genre
import com.tbc.bookli.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    operator fun invoke(): Flow<Resource<List<Genre>>> {
        return bookRepository.getGenres()
    }
}