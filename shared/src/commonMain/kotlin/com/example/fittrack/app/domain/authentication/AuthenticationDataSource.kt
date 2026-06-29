package com.example.fittrack.app.domain.authentication

import com.example.fittrack.app.domain.jwt.TokenPair
import com.example.fittrack.core.data.util.NetworkError
import com.example.fittrack.core.data.util.Result

interface AuthenticationDataSource {
    suspend fun login(loginRequest: LoginRequest): Result<TokenPair, NetworkError>
    suspend fun register(registerRequest: RegisterRequest): Result<Any, NetworkError>
    suspend fun logout(logoutRequest: LogoutRequest): Result<Any, NetworkError>
}