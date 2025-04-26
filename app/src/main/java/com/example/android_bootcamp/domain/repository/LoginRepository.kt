package com.example.android_bootcamp.domain.repository

import com.example.android_bootcamp.common.Resource
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun loginUser(email: String, password: String): Flow<Resource<Unit>>
}
