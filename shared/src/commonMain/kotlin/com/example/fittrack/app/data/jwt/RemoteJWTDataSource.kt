package com.example.fittrack.app.data.jwt

import com.example.fittrack.app.domain.jwt.JWTDataSource
import com.example.fittrack.app.domain.jwt.RefreshRequest
import com.example.fittrack.app.domain.jwt.TokenPair
import com.example.fittrack.core.data.getApiKey
import com.example.fittrack.core.data.getNGROKKey
import com.example.fittrack.core.data.safeCall
import com.example.fittrack.core.data.util.NetworkError
import com.example.fittrack.core.data.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType

class RemoteJWTDataSource(
    private val httpClient: HttpClient
): JWTDataSource {
    override suspend fun refreshTokens(refreshToken: String): Result<TokenPair, NetworkError> {
        return safeCall<TokenPair> {
            httpClient.post(
                urlString = "${getNGROKKey()}/auth/refresh"
            ) {
                contentType(type = ContentType.Application.Json)
                setBody(
                    body = RefreshRequest(
                        refreshToken = refreshToken
                    )
                )
            }
        }
    }

}