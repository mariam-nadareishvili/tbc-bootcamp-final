package com.tbc.bookli.domain.useCase

import com.tbc.bookli.data.local.store.PreferenceStore
import javax.inject.Inject

class SetDarkModeUseCase @Inject constructor(private val preferenceStore: PreferenceStore) {

    suspend operator fun invoke(isDarkMode: Boolean) {
        preferenceStore.saveDarkMode(isDarkMode)
    }
}
