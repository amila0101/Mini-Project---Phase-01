package com.sm.travelapp.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6135FE),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFECE5FF),
    onPrimaryContainer = Color(0xFF4618DB),
    secondary = Color(0xFF6135FE),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFECE5FF),
    onSecondaryContainer = Color(0xFF4618DB),
    tertiary = Color(0xFF6135FE),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFECE5FF),
    onTertiaryContainer = Color(0xFF4618DB),
    background = Color.White,
    onBackground = Color(0xFF1C1B1F),
    surface = Color.White,
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFF3F3F3),
    onSurfaceVariant = Color(0xFF49454F),
    outline = Color(0xFF79747E)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF6135FE),
    onPrimary = Color.White,
    primaryContainer = Color(0xFF4618DB),
    onPrimaryContainer = Color(0xFFECE5FF),
    secondary = Color(0xFF6135FE),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF4618DB),
    onSecondaryContainer = Color(0xFFECE5FF),
    tertiary = Color(0xFF6135FE),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF4618DB),
    onTertiaryContainer = Color(0xFFECE5FF),
    background = Color(0xFF1C1B1F),
    onBackground = Color.White,
    surface = Color(0xFF1C1B1F),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    outline = Color(0xFF938F99)
)

@Composable
fun TravelAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}