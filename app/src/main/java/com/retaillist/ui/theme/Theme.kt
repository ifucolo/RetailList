package com.retaillist.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = darkColors(
    primary = gray900,
    secondary = rust600,
    background = taupe100,
    surface = white850,
    onPrimary = white,
    onSecondary = white,
    onBackground = taupe800,
    onSurface = gray800
)

private val DarkColorPalette = lightColors(
    primary = white,
    secondary = rust300,
    background = gray900,
    surface = white150,
    onPrimary = gray900,
    onSecondary = gray900,
    onBackground = taupe100,
    onSurface = white800
)

@Composable
fun MyTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
