package com.tbc.bookli.domain.repository

import kotlinx.coroutines.flow.Flow

interface CacheRepository {
    fun getRememberMe(): Flow<Boolean>
    suspend fun saveRememberMe(rememberMe: Boolean)
    suspend fun clearPreferences()
    fun getAppLanguage(): Flow<String>
    suspend fun updateLanguage(language: String)
    fun isDarkMode(): Flow<Boolean> // Get dark mode preference
    suspend fun setDarkMode(isDarkMode: Boolean) // Save dark mode preference
}
