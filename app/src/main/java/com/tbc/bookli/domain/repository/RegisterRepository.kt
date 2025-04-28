package com.tbc.bookli.domain.repository

import com.tbc.bookli.common.Resource
import kotlinx.coroutines.flow.Flow

interface RegisterRepository {
    suspend fun registerUser(
        email: String,
        password: String,
    ): Flow<Resource<Unit>>
}
