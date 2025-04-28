package com.example.android_bootcamp

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColors = lightColorScheme(
    primary = Color(0xFF57A0E7),
    secondary = Color(0xFF68CCDE),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFEEEEEE),
    onBackground = Color(0xFF121212)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF57A0E7),
    secondary = Color(0xFF68CCDE),
    background = Color(0xFF121212),
    surface = Color(0xFF121212),
    onBackground = Color(0xFFFFFFFF)
)

@Composable
fun AppTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color.Black,
        darkIcons = false
    )

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}