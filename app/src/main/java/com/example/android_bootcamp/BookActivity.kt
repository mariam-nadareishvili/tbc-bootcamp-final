package com.example.android_bootcamp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.android_bootcamp.presentation.helper.LocaleHelper
import com.example.android_bootcamp.presentation.navigation.AppNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookActivity : AppCompatActivity() {

    private val bookViewModel: BookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isDarkModeEnabled by bookViewModel.isDarkModeEnabled.collectAsStateWithLifecycle()
            val currentLanguage by bookViewModel.currentLanguage.collectAsStateWithLifecycle()
            val context = LocalContext.current

            LaunchedEffect(currentLanguage) {
                LocaleHelper.setLocale(context = context, language = currentLanguage)
            }

            AppTheme(darkTheme = isSystemInDarkTheme() || isDarkModeEnabled) {
                AppNavGraph()
            }
        }
    }
}