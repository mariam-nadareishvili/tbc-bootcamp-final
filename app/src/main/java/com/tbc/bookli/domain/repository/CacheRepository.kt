package com.tbc.bookli.domain.repository

import kotlinx.coroutines.flow.Flow

interface CacheRepository {
    fun getRememberMe(): Flow<Boolean>
    suspend fun saveRememberMe(rememberMe: Boolean)
    suspend fun clearRememberMe()
    fun getAppLanguage(): Flow<String>
    suspend fun updateLanguage(language: String)
    fun isDarkMode(): Flow<Boolean>
    suspend fun setDarkMode(isDarkMode: Boolean)
    fun getIntroSeen(): Flow<Boolean>
    suspend fun saveIntroSeen(seen: Boolean)
}
