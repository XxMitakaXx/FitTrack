package com.example.fittrack.user_dimensions_data_collect.data

import com.example.fittrack.app.data.jwt.JWTUtil
import com.example.fittrack.core.data.getApiKey
import com.example.fittrack.core.data.safeCall
import com.example.fittrack.core.data.util.NetworkError
import com.example.fittrack.user_dimensions_data_collect.domain.UserDimensionsDataSource
import com.example.fittrack.user_dimensions_data_collect.domain.models.UserDimensionsDataDTO
import io.ktor.client.HttpClient
import com.example.fittrack.core.data.util.Result
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class RemoteUserDimensionsDataSource(
    private val httpClient: HttpClient,
    private val jwtUtil: JWTUtil

): UserDimensionsDataSource {
    override suspend fun saveUserDimensionsData(userDimensionsDataDTO: UserDimensionsDataDTO): Result<Unit, NetworkError> {
        return safeCall<Unit> {
            httpClient.post(
                urlString = "${getApiKey()}/users/user-starter-training-data"
            ) {
                header(key = "Authorization", value = "Bearer ${jwtUtil.tokenPair!!.accessToken}")
                contentType(ContentType.Application.Json)
                setBody(body = userDimensionsDataDTO)
            }
        }
    }
}