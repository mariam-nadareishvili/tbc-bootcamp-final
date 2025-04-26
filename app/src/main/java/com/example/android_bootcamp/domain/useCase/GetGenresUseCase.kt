package com.example.android_bootcamp.domain.useCase

import com.example.android_bootcamp.common.Resource
import com.example.android_bootcamp.domain.model.Genre
import com.example.android_bootcamp.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    operator fun invoke(): Flow<Resource<List<Genre>>> {
        return bookRepository.getGenres()
    }
}