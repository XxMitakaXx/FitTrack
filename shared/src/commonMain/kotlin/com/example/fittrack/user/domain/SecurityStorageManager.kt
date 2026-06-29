package com.example.fittrack.user.domain

import com.example.fittrack.app.domain.jwt.TokenPair
import kotlinx.coroutines.flow.Flow

expect class SecurityStorageManager {
    suspend fun saveCredentials(tokenPair: TokenPair)
    fun getCredentials(): Flow<TokenPair>
    suspend fun clearCredentials()
}