package com.example.android_bootcamp.data.remote.sevice

import com.example.android_bootcamp.data.remote.model.BookDto
import com.example.android_bootcamp.data.remote.model.FeedBookDto
import com.example.android_bootcamp.data.remote.model.GenreDto
import com.example.android_bootcamp.data.remote.model.StoryDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApiService {

    @GET("books-bff")
    suspend fun searchBooks(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<FeedBookDto>

    @GET("stories")
    suspend fun getStories(): Response<List<StoryDto>>

    @GET("genres")
    suspend fun getGenres(): Response<List<GenreDto>>

    @GET("books")
    suspend fun searchBooks(
        @Query("search") search: String
    ): Response<List<BookDto>>

    @GET("books/{id}")
    suspend fun getBookById(@Path("id") bookId: String): Response<BookDto>
}