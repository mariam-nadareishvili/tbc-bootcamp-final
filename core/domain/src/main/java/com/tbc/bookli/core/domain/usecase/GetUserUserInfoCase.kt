package com.tbc.bookli.core.domain.usecase

import com.tbc.bookli.core.domain.Resource
import com.tbc.bookli.core.domain.model.User
import com.tbc.bookli.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUserInfoCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(id: String): Flow<Resource<User>> {
        return userRepository.getUserInfo(id = id)
    }
}