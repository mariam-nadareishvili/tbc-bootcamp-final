package com.example.android_bootcamp.data.local.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class PreferenceStore @Inject constructor(@ApplicationContext private val context: Context) {

    companion object {
        private val REMEMBER_ME_KEY = booleanPreferencesKey("remember_me")
        private val LANGUAGE_KEY = stringPreferencesKey("language")
    }

    val language: Flow<String> = context.dataStore.data
        .map { preferences ->
            preferences[LANGUAGE_KEY] ?: "en"
        }

    suspend fun saveLanguage(language: String) {
        context.dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language
        }
    }

    suspend fun saveRememberMe(value: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[REMEMBER_ME_KEY] = value
        }
    }

    fun getRememberMe(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[REMEMBER_ME_KEY] ?: false
        }
    }

    suspend fun clearRememberMe() {
        context.dataStore.edit { preferences ->
            preferences.remove(REMEMBER_ME_KEY)
        }
    }
}