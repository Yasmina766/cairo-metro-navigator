package com.yasminagbreil.metro.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = Primary,
    background = BackgroundLight,
    surface = SurfaceLight,
    onBackground = TextLight,
    onSurface = TextLight,
    error = ErrorRed
)

private val DarkColors = darkColorScheme(
    primary = PrimaryDark,
    background = BackgroundDark,
    surface = SurfaceDark,
    onBackground = TextDark,
    onSurface = TextDark,
    error = ErrorRed
)

@Composable
fun CairoMetroNavigatorTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}