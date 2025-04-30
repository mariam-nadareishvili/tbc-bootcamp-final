package com.tbc.bookli.core.domain.repository

import androidx.paging.PagingData
import com.tbc.bookli.core.domain.Resource
import com.tbc.bookli.core.domain.model.Book
import com.tbc.bookli.core.domain.model.FeedBook
import com.tbc.bookli.core.domain.model.Genre
import com.tbc.bookli.core.domain.model.Story
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getFeedBooks(): Flow<PagingData<FeedBook>>
    fun getStories(): Flow<Resource<List<Story>>>
    fun getGenres(): Flow<Resource<List<Genre>>>
    fun searchBooks(search: String): Flow<Resource<List<Book>>>
    fun getAllBooks(): Flow<Resource<List<Book>>>
    fun getBookById(id: String): Flow<Resource<Book>>
    fun updateBookById(book: Book): Flow<Resource<Unit>>
}