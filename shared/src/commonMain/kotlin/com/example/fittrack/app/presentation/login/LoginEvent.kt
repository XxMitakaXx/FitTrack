package com.example.fittrack.app.presentation.login

import com.example.fittrack.core.data.util.NetworkError

sealed interface LoginEvent {
    data object OnSuccessfulLogin: LoginEvent
    data object Logout: LoginEvent
    data class Error(val error: NetworkError): LoginEvent
}