package com.tbc.bookli.domain.useCase

import com.tbc.bookli.common.Resource
import com.tbc.bookli.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
) {
    suspend operator fun invoke(
        email: String,
        password: String,
        isRememberedMeChecked: Boolean
    ): Flow<Resource<Unit>> {
        return loginRepository.loginUser(email, password)
    }
}