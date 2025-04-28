package com.tbc.bookli.domain.repository

import androidx.paging.PagingData
import com.tbc.bookli.common.Resource
import com.tbc.bookli.domain.model.Book
import com.tbc.bookli.domain.model.FeedBook
import com.tbc.bookli.domain.model.Genre
import com.tbc.bookli.domain.model.Story
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getFeedBooks(): Flow<PagingData<FeedBook>>
    fun getStories(): Flow<Resource<List<Story>>>
    fun getGenres(): Flow<Resource<List<Genre>>>
    fun searchBooks(search: String): Flow<Resource<List<Book>>>
    fun getBookById(id: String): Flow<Resource<Book>>
}