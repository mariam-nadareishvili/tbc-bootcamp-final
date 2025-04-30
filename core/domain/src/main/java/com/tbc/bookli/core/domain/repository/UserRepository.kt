package com.tbc.bookli.core.domain.repository

import com.tbc.bookli.core.domain.Resource
import com.tbc.bookli.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserInfo(id: String): Flow<Resource<User>>
    fun updateUserInfo(user: User): Flow<Resource<Unit>>
}