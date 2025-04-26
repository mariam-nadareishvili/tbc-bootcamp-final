package com.example.android_bootcamp.domain.useCase

import androidx.paging.PagingData
import com.example.android_bootcamp.domain.model.FeedBook
import com.example.android_bootcamp.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFeedBooksUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    operator fun invoke(): Flow<PagingData<FeedBook>> {
        return bookRepository.getFeedBooks()
    }
}
