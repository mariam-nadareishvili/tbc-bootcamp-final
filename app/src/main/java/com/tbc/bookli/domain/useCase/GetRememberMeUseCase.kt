package com.tbc.bookli.domain.useCase

import com.tbc.bookli.domain.repository.CacheRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRememberMeUseCase @Inject constructor(
    private val cacheRepository: CacheRepository
) {
    operator fun invoke(): Flow<Boolean> = cacheRepository.getRememberMe()
}