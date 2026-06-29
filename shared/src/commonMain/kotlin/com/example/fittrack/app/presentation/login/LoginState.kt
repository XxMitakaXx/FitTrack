package com.example.fittrack.app.presentation.login

import androidx.compose.runtime.Immutable

@Immutable
data class LoginState(
    val email: String = "",
    val password: String = "",
    val badCredentials: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isLoggedIn: Boolean = false
)
