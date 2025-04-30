package com.tbc.bookli.core.domain.usecase

import com.tbc.bookli.core.domain.Resource
import com.tbc.bookli.core.domain.model.Book
import com.tbc.bookli.core.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookByIdUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    operator fun invoke(id: String): Flow<Resource<Book>> {
        return bookRepository.getBookById(id = id)
    }
}