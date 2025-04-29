package com.tbc.bookli.domain.useCase

import com.tbc.bookli.common.Resource
import com.tbc.bookli.domain.model.Book
import com.tbc.bookli.domain.repository.BookRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class UpdateBookByIdUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    operator fun invoke(book: Book): Flow<Resource<Unit>> {
        return bookRepository.updateBookById(book)
    }
}
