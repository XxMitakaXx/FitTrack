package com.example.fittrack.app.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val GreenPrimaryLight = Color(0xFF2E7D32)      // Deep, readable green
val GreenOnPrimaryLight = Color(0xFFFFFFFF)    // White text/icons on primary green
val GreenContainerLight = Color(0xFFA5D6A7)    // Soft green for secondary containers
val BackgroundLight = Color(0xFFFFFFFF)        // Pure white background
val SurfaceLight = Color(0xFFF7F9F7)           // Very slight off-white for cards/surfaces
val TextPrimaryLight = Color(0xFF121212)       // Near-black text for readability

// --- Dark Theme Colors (Green & Black) ---
val GreenPrimaryDark = Color(0xFF81C784)       // Lighter, vibrant green for dark mode
val GreenOnPrimaryDark = Color(0xFF003300)     // Dark green/black text on primary green
val GreenContainerDark = Color(0xFF1B5E20)     // Dark green for secondary containers
val BackgroundDark = Color(0xFF000000)         // Pure black background
val SurfaceDark = Color(0xFF121212)            // Dark grey for elevated surfaces (Material standard)
val TextPrimaryDark = Color(0xFFE0E0E0)

val LightColors = lightColorScheme(
    primary = GreenPrimaryLight,
    onPrimary = GreenOnPrimaryLight,
    primaryContainer = GreenContainerLight,
    onPrimaryContainer = TextPrimaryLight,
    background = BackgroundLight,
    onBackground = TextPrimaryLight,
    surface = SurfaceLight,
    onSurface = TextPrimaryLight,
    // Optional: Define standard error colors
    error = Color(0xFFB3261E),
    onError = Color(0xFFFFFFFF)
)

val colorScheme = darkColorScheme(
    primary = GreenPrimaryDark,
    onPrimary = GreenOnPrimaryDark,
    primaryContainer = GreenContainerDark,
    onPrimaryContainer = TextPrimaryDark,
    background = BackgroundDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    // Optional: Define standard error colors
    error = Color(0xFFF2B8B5),
    onError = Color(0xFF601410)
)