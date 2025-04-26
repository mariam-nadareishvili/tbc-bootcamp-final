package com.example.android_bootcamp.domain.repository

import androidx.paging.PagingData
import com.example.android_bootcamp.common.Resource
import com.example.android_bootcamp.data.remote.model.BookDto
import com.example.android_bootcamp.data.remote.model.FeedBookDto
import com.example.android_bootcamp.data.remote.model.GenreDto
import com.example.android_bootcamp.data.remote.model.StoryDto
import com.example.android_bootcamp.domain.model.Book
import com.example.android_bootcamp.domain.model.FeedBook
import com.example.android_bootcamp.domain.model.Genre
import com.example.android_bootcamp.domain.model.Story
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun getFeedBooks(): Flow<PagingData<FeedBook>>
    fun getStories(): Flow<Resource<List<Story>>>
    fun getGenres(): Flow<Resource<List<Genre>>>
    fun searchBooks(search: String): Flow<Resource<List<Book>>>
    fun getBookById(id: String): Flow<Resource<Book>>
}