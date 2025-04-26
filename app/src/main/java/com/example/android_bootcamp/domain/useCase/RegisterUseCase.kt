package com.example.android_bootcamp.domain.useCase

import com.example.android_bootcamp.common.Resource
import com.example.android_bootcamp.domain.repository.RegisterRepository
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