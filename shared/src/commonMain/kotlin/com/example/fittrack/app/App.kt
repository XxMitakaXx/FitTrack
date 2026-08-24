package com.example.fittrack.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.fittrack.app.navigation.AppNavigation
import com.example.fittrack.app.theme.colorScheme

@Composable
@Preview
fun App() {
    MaterialTheme(
        colorScheme = colorScheme
    ) {
        AppNavigation(
            navController = rememberNavController()
        )
    }
}