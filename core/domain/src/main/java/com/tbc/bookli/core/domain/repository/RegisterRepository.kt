package com.tbc.bookli.core.domain.repository

import com.tbc.bookli.core.domain.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun registerUser(
        email: String,
        password: String,
    ): Flow<Resource<Unit>>
}
