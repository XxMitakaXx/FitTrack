package com.example.fittrack.welcome_page.presentation

sealed interface WelcomeEvent {
    data object OnGoToLogin: WelcomeEvent
    data object OnGoToRegister: WelcomeEvent
}