package com.example.fittrack.app.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrack.app.data.jwt.JWTUtil
import com.example.fittrack.app.domain.authentication.LoginRequest
import com.example.fittrack.app.domain.authentication.AuthenticationDataSource
import com.example.fittrack.app.domain.authentication.LogoutRequest
import com.example.fittrack.core.data.util.onError
import com.example.fittrack.core.data.util.onSuccess
import com.example.fittrack.user.domain.SecurityStorageManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authenticationDataSource: AuthenticationDataSource,
    private val jwtUtil: JWTUtil,
    private val securityStorageManager: SecurityStorageManager
): ViewModel() {
    private val _state = MutableStateFlow(value = LoginState())
    val state = _state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 1000L),
            initialValue = LoginState()
        )

    private val _events = Channel<LoginEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: LoginAction) {
        when(action) {
            is LoginAction.OnEmailValueChange -> editEmail(action.email)
            is LoginAction.OnPasswordValueChange -> editPassword(action.password)
            is LoginAction.OnTogglePasswordVisibility -> togglePasswordVisibility()
            is LoginAction.OnLogin -> login()
            is LoginAction.OnLogout -> logout()
            else -> Unit
        }
    }

    private fun logout() {
        viewModelScope.launch {
            _state.update { it.copy(
                isLoggedIn = false
            ) }

            val refreshToken = jwtUtil.tokenPair?.refreshToken
            if (refreshToken != null) {
                authenticationDataSource.logout(
                    logoutRequest = LogoutRequest(
                        refreshToken = refreshToken
                    )
                )
            }

            jwtUtil.clearTokens()
            securityStorageManager.clearCredentials()

            _events.send(LoginEvent.Logout)
        }
    }

    private fun login() {
        viewModelScope.launch {
            authenticationDataSource.login(
                loginRequest = LoginRequest(
                    email = _state.value.email.trim(),
                    password = _state.value.password.trim()
                )
            )
                .onSuccess { tokenPair ->
                    _state.update { it.copy(
                        badCredentials = false,
                        isLoggedIn = true,
                        email = "",
                        password = ""
                    ) }

                    jwtUtil.saveTokens(tokenPair)
                    securityStorageManager.saveCredentials(tokenPair)

                    _events.send(element = LoginEvent.OnSuccessfulLogin)
                }
                .onError { error ->
                    _state.update { it.copy(
                        badCredentials = true
                    ) }

                    _events.send(element = LoginEvent.Error(error = error))
                }
        }
    }

    private fun togglePasswordVisibility() {
        viewModelScope.launch {
            _state.update { it.copy(
                isPasswordVisible = !_state.value.isPasswordVisible
            ) }
        }
    }

    private fun editPassword(password: String) {
        viewModelScope.launch {
            _state.update { it.copy(
                password = password
            ) }
        }
    }

    private fun editEmail(email: String) {
        viewModelScope.launch {
            _state.update { it.copy(
                email = email
            ) }
        }
    }
}