package com.yasminagbreil.metro.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Primary,
    onPrimary = Color.White,
    secondary = Primary,
    onSecondary = Color.White,
    tertiary = Line1Blue,
    background = BackgroundLight,
    onBackground = TextLight,
    surface = SurfaceLight,
    onSurface = TextLight,
    surfaceVariant = SurfaceVariantLight,
    onSurfaceVariant = TextSecondaryLight,
    surfaceTint = Color.Transparent,
    outline = OutlineLight,
    outlineVariant = SurfaceVariantLight,
    error = ErrorRed,
    onError = Color.White
)

private val DarkColors = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = Color.White,
    secondary = PrimaryDark,
    onSecondary = Color.White,
    tertiary = Line1Blue,
    background = BackgroundDark,
    onBackground = TextDark,
    surface = SurfaceDark,
    onSurface = TextDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondaryDark,
    surfaceTint = Color.Transparent,
    outline = OutlineDark,
    outlineVariant = OutlineDark,
    error = ErrorRed,
    onError = Color.White
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