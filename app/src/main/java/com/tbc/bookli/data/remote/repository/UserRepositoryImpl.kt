package com.tbc.bookli.data.remote.repository

import com.tbc.bookli.common.Resource
import com.tbc.bookli.common.mapResource
import com.tbc.bookli.data.mapper.toDomain
import com.tbc.bookli.data.mapper.toDto
import com.tbc.bookli.data.remote.safeApiCall
import com.tbc.bookli.data.remote.sevice.UserApiService
import com.tbc.bookli.domain.model.User
import com.tbc.bookli.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: UserApiService
) : UserRepository {
    override fun getUserInfo(id: String): Flow<Resource<User>> {
        return safeApiCall {
            apiService.getUserInfo(userId = id)
        }.mapResource { it.toDomain() }
    }

    override fun updateUserInfo(user: User): Flow<Resource<Unit>> {
        return safeApiCall {
            apiService.updateUserInfo(userId = user.id, user = user.toDto())
        }
    }
}