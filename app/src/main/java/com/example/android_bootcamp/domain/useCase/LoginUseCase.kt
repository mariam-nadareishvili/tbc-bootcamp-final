package com.example.android_bootcamp.domain.useCase

import com.example.android_bootcamp.common.Resource
import com.example.android_bootcamp.domain.repository.LoginRepository
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