package com.example.android_bootcamp.domain.repository

import com.example.android_bootcamp.common.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun registerUser(
        email: String,
        password: String,
    ): Flow<Resource<Unit>>
}
