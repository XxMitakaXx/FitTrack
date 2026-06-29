package com.example.fittrack.app.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationRoute {
    @Serializable
    data object TrainingScreen: NavigationRoute

    @Serializable
    data object LoginScreen: NavigationRoute

    @Serializable
    data object RegisterScreen: NavigationRoute
}