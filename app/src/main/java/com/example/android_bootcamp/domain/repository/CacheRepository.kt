package com.example.android_bootcamp.domain.repository

import kotlinx.coroutines.flow.Flow

interface CacheRepository {
    fun getRememberMe(): Flow<Boolean>
    suspend fun saveRememberMe(rememberMe: Boolean)
    suspend fun clearPreferences()
    fun getAppLanguage(): Flow<String>
    suspend fun updateLanguage(language: String)
}
