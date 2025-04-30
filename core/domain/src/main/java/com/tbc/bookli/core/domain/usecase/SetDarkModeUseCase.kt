package com.tbc.bookli.core.domain.usecase

import com.tbc.bookli.core.domain.repository.CacheRepository
import javax.inject.Inject

class SetDarkModeUseCase @Inject constructor(private val cacheRepository: CacheRepository) {

    suspend operator fun invoke(isDarkMode: Boolean) {
        cacheRepository.setDarkMode(isDarkMode)
    }
}
