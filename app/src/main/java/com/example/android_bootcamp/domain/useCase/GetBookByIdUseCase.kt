package com.example.android_bootcamp.domain.useCase

import com.example.android_bootcamp.common.Resource
import com.example.android_bootcamp.domain.model.Book
import com.example.android_bootcamp.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookByIdUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    operator fun invoke(id: String): Flow<Resource<Book>> {
        return bookRepository.getBookById(id = id)
    }
}