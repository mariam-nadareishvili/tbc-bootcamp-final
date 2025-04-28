package com.tbc.bookli.domain.useCase

import com.tbc.bookli.data.local.store.PreferenceStore
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDarkModeUseCase @Inject constructor(private val preferenceStore: PreferenceStore) {

    // Get current dark mode preference
    operator fun invoke(): Flow<Boolean> = preferenceStore.isDarkMode
}
