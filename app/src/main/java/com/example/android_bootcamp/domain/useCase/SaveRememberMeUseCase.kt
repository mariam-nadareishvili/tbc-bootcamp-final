package com.example.android_bootcamp.domain.useCase

import com.example.android_bootcamp.domain.repository.CacheRepository
import javax.inject.Inject

class SaveRememberMeUseCase @Inject constructor(
    private val cacheRepository: CacheRepository
) {
    suspend operator fun invoke(rememberMe: Boolean) {
        cacheRepository.saveRememberMe(rememberMe)
    }
}