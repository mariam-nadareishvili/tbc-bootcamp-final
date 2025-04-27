package com.example.android_bootcamp.domain.useCase

import com.example.android_bootcamp.data.local.store.PreferenceStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDarkModeUseCase @Inject constructor(private val preferenceStore: PreferenceStore) {

    // Get current dark mode preference
    operator fun invoke(): Flow<Boolean> = preferenceStore.isDarkMode
}
