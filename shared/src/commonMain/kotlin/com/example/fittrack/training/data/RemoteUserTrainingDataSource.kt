package com.example.fittrack.training.data

import com.example.fittrack.app.data.jwt.JWTUtil
import com.example.fittrack.core.data.getApiKey
import com.example.fittrack.core.data.safeCall
import com.example.fittrack.core.data.util.NetworkError
import com.example.fittrack.core.data.util.Result
import com.example.fittrack.training.domain.UserTrainingDataSource
import com.example.fittrack.training.domain.models.dtos.ProgressBodyWeightDTO
import com.example.fittrack.training.domain.models.dtos.UserTrainingDataDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class RemoteUserTrainingDataSource(
    private val httpClient: HttpClient,
    private val jwtUtil: JWTUtil
): UserTrainingDataSource {
    override suspend fun fetchUserTrainingData(): Result<UserTrainingDataDTO, NetworkError> {
        return safeCall<UserTrainingDataDTO> {
            httpClient.get(
                urlString = "${getApiKey()}/users/user-training-data"
            ) {
                header(key = "Authorization", value = "Bearer ${jwtUtil.tokenPair?.accessToken}")
            }
        }
    }

    override suspend fun saveUserBodyWeight(progressBodyWeightDTO: ProgressBodyWeightDTO): Result<Unit, NetworkError> {
        return safeCall<Unit> {
            httpClient.post(
                urlString = "${getApiKey()}/users/progress_weight"
            ) {
                header(key = "Authorization", value = "Bearer ${jwtUtil.tokenPair?.accessToken}")
                contentType(type = ContentType.Application.Json)
                setBody(body = progressBodyWeightDTO)
            }
        }
    }

    override suspend fun deleteUserBodyWeight(progressBodyWeightDTO: ProgressBodyWeightDTO): Result<Unit, NetworkError> {
        return safeCall<Unit> {
            httpClient.delete(
                urlString = "${getApiKey()}/users/progress_bodyweight"
            ) {
                header("Authorization", "Bearer ${jwtUtil.tokenPair?.accessToken}")
                contentType(type = ContentType.Application.Json)
                setBody(body = progressBodyWeightDTO)
            }
        }
    }
}