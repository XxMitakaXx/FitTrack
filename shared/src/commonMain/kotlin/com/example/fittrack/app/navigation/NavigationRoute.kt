package com.example.fittrack.app.navigation

import com.example.fittrack.training.domain.models.ProgressBodyWeight
import kotlinx.serialization.Serializable

sealed interface NavigationRoute {

    @Serializable
    data object WelcomeScreen: NavigationRoute

    @Serializable
    data object TrainingScreen: NavigationRoute

    @Serializable
    data object ProgramsScreen: NavigationRoute

    @Serializable
    data object AnalyticsScreen: NavigationRoute

    @Serializable
    data object LoginScreen: NavigationRoute

    @Serializable
    data object RegisterScreen: NavigationRoute

    @Serializable
    data object AddUserTrainingDataScreen: NavigationRoute

    @Serializable
    data class BodyWeightProgressScreen(
        val progressBodyWeightsJson: String
    )

    @Serializable
    data object AddExerciseVarietyScreen
}