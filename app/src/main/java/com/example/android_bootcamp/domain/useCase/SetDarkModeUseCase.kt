package com.example.android_bootcamp.domain.useCase

import com.example.android_bootcamp.data.local.store.PreferenceStore
import javax.inject.Inject

class SetDarkModeUseCase @Inject constructor(private val preferenceStore: PreferenceStore) {

    suspend operator fun invoke(isDarkMode: Boolean) {
        preferenceStore.saveDarkMode(isDarkMode)
    }
}
