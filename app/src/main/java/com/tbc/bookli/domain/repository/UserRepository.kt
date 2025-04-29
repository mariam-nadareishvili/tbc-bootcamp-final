package com.tbc.bookli.domain.repository

import com.tbc.bookli.common.Resource
import com.tbc.bookli.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUserInfo(id: String): Flow<Resource<User>>
    fun updateUserInfo(user: User): Flow<Resource<Unit>>
}