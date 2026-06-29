package com.example.fittrack.user.domain

import com.example.fittrack.app.domain.jwt.TokenPair
import com.russhwolf.settings.ExperimentalSettingsImplementation
import com.russhwolf.settings.KeychainSettings
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf

actual class SecurityStorageManager {
    @OptIn(ExperimentalSettingsImplementation::class)
    private val settings: Settings = KeychainSettings()

    companion object {
        private val ENCRYPTED_ACCESS_TOKEN_KEY = "encrypted_access_token"
        private val ENCRYPTED_REFRESH_TOKEN_KEY = "encrypted_refresh_token"
    }

    actual suspend fun saveCredentials(tokenPair: TokenPair) {
        settings.putString(ENCRYPTED_ACCESS_TOKEN_KEY, tokenPair.accessToken)
        settings.putString(ENCRYPTED_REFRESH_TOKEN_KEY, tokenPair.refreshToken)
    }

    actual fun getCredentials(): Flow<TokenPair> {
        val accessToken = settings.getStringOrNull(ENCRYPTED_ACCESS_TOKEN_KEY)
        val refreshToken = settings.getStringOrNull(ENCRYPTED_REFRESH_TOKEN_KEY)

        if (!accessToken.isNullOrEmpty() && !refreshToken.isNullOrEmpty()) {
            val tokenPair = TokenPair(
                accessToken = accessToken,
                refreshToken = refreshToken
            )
            return flowOf(value = tokenPair)
        }

        return emptyFlow()
    }

    actual suspend fun clearCredentials() {
        settings.clear()
    }
}