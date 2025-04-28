package com.tbc.bookli.domain.useCase

import androidx.paging.PagingData
import com.tbc.bookli.domain.model.FeedBook
import com.tbc.bookli.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFeedBooksUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    operator fun invoke(): Flow<PagingData<FeedBook>> {
        return bookRepository.getFeedBooks()
    }
}
