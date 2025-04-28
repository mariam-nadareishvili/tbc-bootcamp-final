package com.example.android_bootcamp.presentation.helper

import android.app.Activity
import android.content.Context
import java.util.Locale

object LocaleHelper {

    fun setLocale(context: Context, language: String) {
        val currentLocale =
            context.resources.configuration.locales.get(0) ?: Locale.getDefault()

        if (currentLocale.language != language) {
            val locale = Locale(language)
            Locale.setDefault(locale)

            val resources = context.resources
            val configuration = resources.configuration
            configuration.setLocale(locale)
            resources.updateConfiguration(configuration, resources.displayMetrics)

            (context as? Activity)?.recreate()
        }
    }
}