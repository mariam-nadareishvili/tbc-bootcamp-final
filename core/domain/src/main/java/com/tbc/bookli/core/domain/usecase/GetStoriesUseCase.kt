package com.tbc.bookli.core.domain.usecase

import com.tbc.bookli.core.domain.Resource
import com.tbc.bookli.core.domain.model.Story
import com.tbc.bookli.core.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStoriesUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
     operator fun invoke(): Flow<Resource<List<Story>>> {
        return bookRepository.getStories()
    }
}