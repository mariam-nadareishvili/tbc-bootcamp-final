package com.tbc.bookli.domain.useCase

import com.tbc.bookli.common.Resource
import com.tbc.bookli.domain.model.Book
import com.tbc.bookli.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookByIdUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    operator fun invoke(id: String): Flow<Resource<Book>> {
        return bookRepository.getBookById(id = id)
    }
}