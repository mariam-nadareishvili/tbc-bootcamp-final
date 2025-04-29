package com.tbc.bookli.data.remote.sevice

import com.tbc.bookli.data.remote.model.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApiService {

    @GET("userInfo/{id}")
    suspend fun getUserInfo(@Path("id") userId: String): Response<UserDto>

    @PUT("userInfo/{id}")
    suspend fun updateUserInfo(@Path("id") userId: String, @Body user: UserDto): Response<Unit>
}