package com.example.fittrack.app.domain.jwt

import com.example.fittrack.core.data.util.NetworkError
import com.example.fittrack.core.data.util.Result

interface JWTDataSource {
    suspend fun refreshTokens(refreshToken: String): Result<TokenPair, NetworkError>
}