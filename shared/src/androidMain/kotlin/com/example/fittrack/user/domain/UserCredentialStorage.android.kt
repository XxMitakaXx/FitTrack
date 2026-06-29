package com.example.fittrack.user.domain

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.fittrack.app.domain.jwt.TokenPair
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.nio.charset.StandardCharsets
import java.util.Base64

actual class SecurityStorageManager(
    private val context: Context
) {
    private val KEYSET_NAME = "secure_datastore_keyset"
    private val PREF_FILE_NAME = "tink_datastore_prefs"
    private val MASTER_KEY_URI = "android-keystore://secure_storage_master_key"

    private val aead: Aead by lazy {
        AeadConfig.register()
        val keysetManager = AndroidKeysetManager.Builder()
            .withSharedPref(context, KEYSET_NAME, PREF_FILE_NAME)
            .withKeyTemplate(KeyTemplates.get("AES256_GCM"))
            .withMasterKeyUri(MASTER_KEY_URI)
            .build()
        keysetManager.keysetHandle.getPrimitive(Aead::class.java)
    }

    private val dataStore: DataStore<Preferences>  = PreferenceDataStoreFactory.create(
        produceFile = { context.preferencesDataStoreFile("secure_credentials") }
    )

    companion object {
        private val ENCRYPTED_ACCESS_TOKEN_KEY = stringPreferencesKey(name = "encrypted_access_token")
        private val ENCRYPTED_REFRESH_TOKEN_KEY = stringPreferencesKey(name = "encrypted_refresh_token")
    }

    private fun encryptString(plainText: String): String {
        val encryptedBytes = aead.encrypt(plainText.toByteArray(charset = StandardCharsets.UTF_8), null)
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    private fun decryptingString(encryptedText: String): String {
        val decodedBytes = Base64.getDecoder().decode(encryptedText)
        val decryptedBytes = aead.decrypt(decodedBytes, null)
        return String(decryptedBytes, StandardCharsets.UTF_8)
    }



    actual suspend fun saveCredentials(tokenPair: TokenPair) {
        val cipherAccessToken = encryptString(plainText = tokenPair.accessToken)
        val cipherRefreshToken = encryptString(plainText = tokenPair.refreshToken)

        dataStore.edit { preferences ->
            preferences[ENCRYPTED_ACCESS_TOKEN_KEY] = cipherAccessToken
            preferences[ENCRYPTED_REFRESH_TOKEN_KEY] = cipherRefreshToken
        }
    }

    actual fun getCredentials(): Flow<TokenPair> = dataStore.data.map { preferences ->
        val cipherAccessToken = preferences[ENCRYPTED_ACCESS_TOKEN_KEY]
        val cipherRefreshToken = preferences[ENCRYPTED_REFRESH_TOKEN_KEY]
        if (!cipherAccessToken.isNullOrEmpty() && !cipherRefreshToken.isNullOrEmpty()) {
            try {
                val decryptedAccessToken = decryptingString(encryptedText = cipherAccessToken)
                val decryptedRefreshToken = decryptingString(encryptedText = cipherRefreshToken)
                TokenPair(
                    accessToken = decryptedAccessToken,
                    refreshToken = decryptedRefreshToken
                )
            } catch (e: Exception) {
                null
            }
        } else {
            null
        } as TokenPair
    }

    actual suspend fun clearCredentials() {
        dataStore.edit { preferences -> preferences.clear() }
    }
}