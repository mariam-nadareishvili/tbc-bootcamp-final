package com.example.android_bootcamp.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.android_bootcamp.common.Resource
import com.example.android_bootcamp.common.mapResource
import com.example.android_bootcamp.data.mapper.toDomain
import com.example.android_bootcamp.data.remote.sevice.BookApiService
import com.example.android_bootcamp.data.remote.model.BookDto
import com.example.android_bootcamp.data.remote.model.FeedBookDto
import com.example.android_bootcamp.data.remote.paging.FeedBookPagingSource
import com.example.android_bootcamp.data.remote.model.GenreDto
import com.example.android_bootcamp.data.remote.model.StoryDto
import com.example.android_bootcamp.data.remote.safeApiCall
import com.example.android_bootcamp.domain.model.Book
import com.example.android_bootcamp.domain.model.FeedBook
import com.example.android_bootcamp.domain.model.Genre
import com.example.android_bootcamp.domain.model.Story
import com.example.android_bootcamp.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override fun getBookById(id: String): Flow<Resource<Book>> {
        return safeApiCall {
            apiService.getBookById(id)
        }.mapResource { it.toDomain() }
    }
}