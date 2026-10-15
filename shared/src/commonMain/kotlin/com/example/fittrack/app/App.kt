package com.example.fittrack.app

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.fittrack.app.navigation.AppNavigation
import com.example.fittrack.app.theme.DarkColors
import com.example.fittrack.app.theme.LightColors

@Composable
@Preview
fun App(
    isSystemDarkTheme: Boolean = isSystemInDarkTheme()
) {
    MaterialTheme(
        colorScheme = if (isSystemDarkTheme) DarkColors else LightColors
    ) {
        AppNavigation(
            navController = rememberNavController()
        )
    }
}