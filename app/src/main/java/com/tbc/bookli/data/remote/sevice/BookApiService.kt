package com.tbc.bookli.data.remote.sevice

import com.tbc.bookli.data.remote.model.BookDto
import com.tbc.bookli.data.remote.model.FeedBookDto
import com.tbc.bookli.data.remote.model.GenreDto
import com.tbc.bookli.data.remote.model.StoryDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface BookApiService {

    @GET("books-bff-new")
    suspend fun searchBooks(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<FeedBookDto>

    @GET("stories")
    suspend fun getStories(): Response<List<StoryDto>>

    @GET("genres")
    suspend fun getGenres(): Response<List<GenreDto>>

    @GET("books-new")
    suspend fun searchBooks(
        @Query("search") search: String
    ): Response<List<BookDto>>

    @GET("books-new/{id}")
    suspend fun getBookById(@Path("id") bookId: String): Response<BookDto>

    @PUT("books-new/{id}")
    suspend fun updateBookById(@Path("id") bookId: String, @Body bookDto: BookDto): Response<Unit>
}