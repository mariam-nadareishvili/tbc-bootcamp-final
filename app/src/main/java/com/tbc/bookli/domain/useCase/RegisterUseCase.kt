package com.tbc.bookli.domain.useCase

import com.tbc.bookli.common.Resource
import com.tbc.bookli.domain.repository.RegisterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Flow<Resource<Unit>> {
        return registerRepository.registerUser(email = email, password = password)
    }

}