package com.example.fittrack.app.data.authentication

import com.example.fittrack.app.domain.authentication.LoginRequest
import com.example.fittrack.app.domain.authentication.AuthenticationDataSource
import com.example.fittrack.app.domain.authentication.LogoutRequest
import com.example.fittrack.app.domain.authentication.RegisterRequest
import com.example.fittrack.app.domain.jwt.TokenPair
import com.example.fittrack.core.data.safeCall
import com.example.fittrack.core.data.util.NetworkError
import com.example.fittrack.core.data.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class RemoteAuthentication(
    private val httpClient: HttpClient
): AuthenticationDataSource {
    override suspend fun login(loginRequest: LoginRequest): Result<TokenPair, NetworkError> {
       return safeCall<TokenPair> {
            httpClient.post(
               urlString = "http://10.0.2.2:8080/auth/login"
           ) {
               contentType(type = ContentType.Application.Json)
               setBody(body = loginRequest)
           }
       }
    }

    override suspend fun register(registerRequest: RegisterRequest): Result<Any, NetworkError> {
        return safeCall<Unit> {
            httpClient.post(
                urlString = "http://10.0.2.2:8080/auth/register"
            ) {
                contentType(type = ContentType.Application.Json)
                setBody(body = registerRequest)
            }
        }
    }

    override suspend fun logout(logoutRequest: LogoutRequest): Result<Any, NetworkError> {
        return safeCall<Unit> {
            httpClient.post(
                urlString = "http://10.0.2.2:8080/auth/logout"
            ) {
                contentType(type = ContentType.Application.Json)
                setBody(body = logoutRequest)
            }
        }
    }
}