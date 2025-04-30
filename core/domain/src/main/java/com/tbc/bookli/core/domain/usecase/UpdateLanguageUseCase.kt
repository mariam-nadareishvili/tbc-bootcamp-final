package com.tbc.bookli.core.domain.usecase

import com.tbc.bookli.core.domain.repository.CacheRepository
import javax.inject.Inject

class UpdateLanguageUseCase @Inject constructor(
    private val cacheRepository: CacheRepository
) {
    suspend operator fun invoke(language: String) {
        cacheRepository.updateLanguage(language)
    }
}