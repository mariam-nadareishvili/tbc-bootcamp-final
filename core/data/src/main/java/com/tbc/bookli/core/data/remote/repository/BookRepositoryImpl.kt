package com.tbc.bookli.core.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.tbc.bookli.core.data.mapper.toDomain
import com.tbc.bookli.core.data.mapper.toDto
import com.tbc.bookli.core.data.remote.paging.FeedBookPagingSource
import com.tbc.bookli.core.data.remote.safeApiCall
import com.tbc.bookli.core.data.remote.sevice.BookApiService
import com.tbc.bookli.core.domain.Resource
import com.tbc.bookli.core.domain.mapResource
import com.tbc.bookli.core.domain.model.Book
import com.tbc.bookli.core.domain.model.FeedBook
import com.tbc.bookli.core.domain.model.Genre
import com.tbc.bookli.core.domain.model.Story
import com.tbc.bookli.core.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val apiService: BookApiService
) : BookRepository {

    override fun getFeedBooks(): Flow<PagingData<FeedBook>> {
        return Pager(
            config = PagingConfig(
                pageSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { FeedBookPagingSource(apiService) }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

    override fun getStories(): Flow<Resource<List<Story>>> {
        return safeApiCall {
            apiService.getStories()
        }.mapResource { it.map { it.toDomain() } }
    }

    override fun getGenres(): Flow<Resource<List<Genre>>> {
        return safeApiCall {
            apiService.getGenres()
        }.mapResource { it.map { it.toDomain() } }

    }

    override fun searchBooks(search: String): Flow<Resource<List<Book>>> {
        return safeApiCall {
            apiService.searchBooks(search)
        }.mapResource { it.map { it.toDomain() } }
    }

    override fun getAllBooks(): Flow<Resource<List<Book>>> {
        return safeApiCall {
            apiService.getAllBooks()
        }.mapResource { it.map { it.toDomain() } }
    }

    override fun getBookById(id: String): Flow<Resource<Book>> {
        return safeApiCall {
            apiService.getBookById(id)
        }.mapResource { it.toDomain() }
    }

    override fun updateBookById(book: Book): Flow<Resource<Unit>> {
        return safeApiCall {
            apiService.updateBookById(bookId = book.id, bookDto = book.toDto())
        }
    }
}