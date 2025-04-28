package com.tbc.bookli.domain.useCase

import com.tbc.bookli.domain.repository.CacheRepository
import javax.inject.Inject

class ClearPreferencesUseCase @Inject constructor(
    private val cacheRepository: CacheRepository
) {
    suspend operator fun invoke() {
        cacheRepository.clearPreferences()
    }
}