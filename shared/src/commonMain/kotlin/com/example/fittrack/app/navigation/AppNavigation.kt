package com.example.fittrack.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fittrack.app.navigation.components.NavigationBar
import com.example.fittrack.app.presentation.login.LoginRoot
import com.example.fittrack.app.presentation.register.RegisterRoot
import com.example.fittrack.body_weight.presentation.BodyWeightRoot
import com.example.fittrack.programs.presentation.ProgramsRoot
import com.example.fittrack.training.presentation.TrainingRoot
import com.example.fittrack.user_dimensions_data_collect.presentation.UserDimensionsDataCollectRoot
import com.example.fittrack.welcome_page.presentation.WelcomeRoot
import kotlinx.serialization.json.Json

@Composable
fun AppNavigation(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = NavigationRoute.WelcomeScreen) {
        composable<NavigationRoute.WelcomeScreen> {
            WelcomeRoot(
                onNavigateToLogin = { navController.navigate(route = NavigationRoute.LoginScreen) },
                onNavigateToRegister = { navController.navigate(route = NavigationRoute.RegisterScreen) }
            )
        }

        composable<NavigationRoute.LoginScreen> {
            LoginRoot(
                onNavigateToRegister = { navController.navigate(route = NavigationRoute.RegisterScreen) },
                onNavigateToTrainingScreen = { navController.navigate(route = NavigationRoute.TrainingScreen) }
            )
        }

        composable<NavigationRoute.RegisterScreen> {
            RegisterRoot(
                onNavigateToLogin = { navController.navigate(route = NavigationRoute.LoginScreen) }
            )
        }

        composable<NavigationRoute.TrainingScreen> {
            TrainingRoot(
                onNavigateToProgramsScreen = { navController.navigate(route = NavigationRoute.ProgramsScreen) },
                onNavigateToAddUserTrainingDataScreen = { navController.navigate(route = NavigationRoute.AddUserTrainingDataScreen) },
                onNavigateToBodyWeightProgressScreen = { progressBodyWights ->
                    val jsonString = Json.encodeToString(value = progressBodyWights)
                    navController.navigate(route = NavigationRoute.BodyWeightProgressScreen(progressBodyWeightsJson = jsonString))
                },
                bottomNavigationBar = {
                    NavigationBar(
                        currentPage = NavigationRoute.TrainingScreen,
                        onGoToTrain = {},
                        onGoToPrograms = { navController.navigate(route = NavigationRoute.ProgramsScreen) },
                        onGoToAnalytics = { navController.navigate(route = NavigationRoute.AnalyticsScreen) }
                    )
                }
            )
        }

        composable<NavigationRoute.ProgramsScreen> {
            ProgramsRoot(
                bottomNavigationBar = {
                    NavigationBar(
                        currentPage = NavigationRoute.ProgramsScreen,
                        onGoToTrain = { navController.navigate(route = NavigationRoute.TrainingScreen) },
                        onGoToPrograms = {},
                        onGoToAnalytics = { navController.navigate(route = NavigationRoute.AnalyticsScreen) }
                    )
                }
            )
        }

        composable<NavigationRoute.AnalyticsScreen> {

        }

        composable<NavigationRoute.AddUserTrainingDataScreen> {
            UserDimensionsDataCollectRoot(
                onNavigateToTrainingScreen = { navController.navigate(route = NavigationRoute.TrainingScreen) }
            )
        }

        composable<NavigationRoute.BodyWeightProgressScreen> {
            BodyWeightRoot(
                onNavigateToTrainingScreen = { navController.navigate(route = NavigationRoute.TrainingScreen) }
            )
        }
    }
}