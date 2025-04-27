package com.example.android_bootcamp.data.local.repository

import com.example.android_bootcamp.data.local.store.PreferenceStore
import com.example.android_bootcamp.domain.repository.CacheRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CacheRepositoryImpl @Inject constructor(
    private val preferenceStore: PreferenceStore
) : CacheRepository {

    override fun getRememberMe(): Flow<Boolean> = preferenceStore.getRememberMe()

    override suspend fun saveRememberMe(rememberMe: Boolean) {
        preferenceStore.saveRememberMe(rememberMe)
    }

    override suspend fun clearPreferences() {
        preferenceStore.clearRememberMe()
    }

    override fun getAppLanguage(): Flow<String> = preferenceStore.language

    override suspend fun updateLanguage(language: String) {
        preferenceStore.saveLanguage(language)
    }
    override fun isDarkMode(): Flow<Boolean> {
        return preferenceStore.isDarkMode
    }

    override suspend fun setDarkMode(isDarkMode: Boolean) {
        preferenceStore.saveDarkMode(isDarkMode)
    }
}
