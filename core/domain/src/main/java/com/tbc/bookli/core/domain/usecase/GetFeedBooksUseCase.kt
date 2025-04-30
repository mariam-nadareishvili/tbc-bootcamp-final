package com.tbc.bookli.core.domain.usecase

import androidx.paging.PagingData
import com.tbc.bookli.core.domain.model.FeedBook
import com.tbc.bookli.core.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFeedBooksUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    operator fun invoke(): Flow<PagingData<FeedBook>> {
        return bookRepository.getFeedBooks()
    }
}
