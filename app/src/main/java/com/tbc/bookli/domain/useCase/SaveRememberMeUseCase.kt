package com.tbc.bookli.domain.useCase

import com.tbc.bookli.domain.repository.CacheRepository
import javax.inject.Inject

class SaveRememberMeUseCase @Inject constructor(
    private val cacheRepository: CacheRepository
) {
    suspend operator fun invoke(rememberMe: Boolean) {
        cacheRepository.saveRememberMe(rememberMe)
    }
}