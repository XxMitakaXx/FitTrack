package com.example.fittrack.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fittrack.app.presentation.login.LoginRoot
import com.example.fittrack.app.presentation.register.RegisterRoot
import com.example.fittrack.training.presentation.home.TrainingRoot

@Composable
fun AppNavigation(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = NavigationRoute.RegisterScreen) {
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
            TrainingRoot()
        }

    }
}