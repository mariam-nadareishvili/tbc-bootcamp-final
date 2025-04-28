package com.tbc.bookli.domain.useCase

import com.tbc.bookli.common.Resource
import com.tbc.bookli.domain.model.Story
import com.tbc.bookli.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStoriesUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
     operator fun invoke(): Flow<Resource<List<Story>>> {
        return bookRepository.getStories()
    }
}