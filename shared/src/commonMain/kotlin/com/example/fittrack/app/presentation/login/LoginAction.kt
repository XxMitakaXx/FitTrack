package com.example.fittrack.app.presentation.login

sealed interface LoginAction {
    data class OnEmailValueChange(val email: String): LoginAction
    data class OnPasswordValueChange(val password: String): LoginAction
    data object OnTogglePasswordVisibility: LoginAction
    data object OnLogin: LoginAction
    data object OnLogout: LoginAction
    data object OnGoToRegister: LoginAction
}