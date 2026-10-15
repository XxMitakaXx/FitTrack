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

val Coral = Color(0xFFD85A30)          // primary button, outer ring, active dot
val DarkCoral = Color(0xFF993C1D)      // middle ring, "Pulse" label
val DeepCoral = Color(0xFF712B13)      // inner ring, chip text
val SoftCoral = Color(0xFFF5C4B3)      // ring tracks (unfilled part)
val LightCoral = Color(0xFFFAECE7)     // hero background

val CoralLight = Color(0xFFF0997B)     // primary on dark (lighter coral for contrast)
val CoralDark900 = Color(0xFF4A1B0C)   // hero background in dark
val CoralDark800 = Color(0xFF712B13)   // ring tracks, chips in dark
val CoralDark100 = Color(0xFFF5C4B3)   // text on dark coral containers

val DarkBackground = Color(0xFF121212)
val DarkSurface = Color(0xFF1C1B1A)
val DarkOnSurface = Color(0xFFF1EFE8)  // headline in dark
val DarkGrayText = Color(0xFFB4B2A9)   // supporting text in dark
val DarkOutline = Color(0xFF5F5E5A)    // outlined button border in dark

// Neutrals
val White = Color(0xFFFFFFFF)          // button text, chips, screen background
val Black = Color(0xFF0B0B0B)          // headline
val Gray = Color(0xFF5F5E5A)           // supporting text
val LightGray = Color(0xFFB4B2A9)      // inactive dots, terms text


val LightColors = lightColorScheme(
    primary = Coral,                    // main buttons, active elements
    onPrimary = White,                  // text on primary buttons
    primaryContainer = LightCoral,      // hero background, soft containers
    onPrimaryContainer = DeepCoral,     // text on those containers

    secondary = DarkCoral,
    onSecondary = White,
    secondaryContainer = SoftCoral,     // ring tracks, chips
    onSecondaryContainer = DeepCoral,

    tertiary = DeepCoral,
    onTertiary = White,

    background = White,
    onBackground = Black,               // headline text

    surface = White,
    onSurface = Black,
    onSurfaceVariant = Gray,            // supporting text

    outline = LightGray,                // outlined button border, inactive dots
    outlineVariant = LightGray
)
val DarkColors = darkColorScheme(
    primary = Coral,                        // keeps the brand color on buttons
    onPrimary = White,
    primaryContainer = CoralDark900,        // hero background
    onPrimaryContainer = CoralDark100,
    secondary = CoralLight,
    onSecondary = CoralDark900,
    secondaryContainer = CoralDark800,      // ring tracks, chips
    onSecondaryContainer = CoralDark100,
    tertiary = CoralLight,
    onTertiary = CoralDark900,
    background = DarkBackground,
    onBackground = DarkOnSurface,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    onSurfaceVariant = DarkGrayText,
    outline = DarkOutline,
    outlineVariant = DarkOutline
)