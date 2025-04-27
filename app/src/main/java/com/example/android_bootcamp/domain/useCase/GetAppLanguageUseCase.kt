package com.example.android_bootcamp.domain.useCase


import com.example.android_bootcamp.domain.repository.CacheRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAppLanguageUseCase @Inject constructor(
    private val cacheRepository: CacheRepository
) {
    operator fun invoke(): Flow<String> = cacheRepository.getAppLanguage()
}