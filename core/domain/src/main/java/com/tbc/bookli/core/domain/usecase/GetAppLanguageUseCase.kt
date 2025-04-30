package com.tbc.bookli.core.domain.usecase


import com.tbc.bookli.core.domain.repository.CacheRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAppLanguageUseCase @Inject constructor(
    private val cacheRepository: CacheRepository
) {
    operator fun invoke(): Flow<String> = cacheRepository.getAppLanguage()
}