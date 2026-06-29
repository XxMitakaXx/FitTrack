package com.example.fittrack.app.data.jwt

import com.example.fittrack.app.domain.jwt.JWTDataSource
import com.example.fittrack.app.domain.jwt.TokenPair
import com.example.fittrack.core.data.util.onError
import com.example.fittrack.core.data.util.onSuccess

class JWTUtil(
    private val jwtDataSource: JWTDataSource
) {
    var tokenPair: TokenPair? = null

    fun saveTokens(tokenPair: TokenPair) {
        this.tokenPair = tokenPair
    }

    suspend fun refreshTokens() {
        this.tokenPair?.refreshToken?.let {
            jwtDataSource.refreshTokens(it)
                .onSuccess { tokenPair ->
                    this.tokenPair = tokenPair
                }
                .onError { error ->

                }
        }
    }

    fun clearTokens() {
        tokenPair = null
    }
}