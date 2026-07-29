package com.example.fittrack.welcome_page.presentation

sealed interface WelcomeAction {
    data object OnGoToLogin: WelcomeAction
    data object OnGoToRegister: WelcomeAction
}