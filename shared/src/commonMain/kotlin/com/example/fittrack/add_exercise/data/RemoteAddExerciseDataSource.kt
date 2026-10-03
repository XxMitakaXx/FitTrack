package com.example.fittrack.add_exercise.data

import com.example.fittrack.add_exercise.domain.AddExerciseDataSource
import com.example.fittrack.add_exercise.domain.models.dto.AddExerciseDTO
import com.example.fittrack.app.data.jwt.JWTUtil
import com.example.fittrack.core.data.getApiKey
import com.example.fittrack.core.data.getNGROKKey
import com.example.fittrack.core.data.safeCall
import com.example.fittrack.core.data.util.NetworkError
import com.example.fittrack.core.data.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class RemoteAddExerciseDataSource(
    private val httpClient: HttpClient,
    private val jwtUtil: JWTUtil
): AddExerciseDataSource {
    override suspend fun saveExercise(addExerciseDTO: AddExerciseDTO): Result<Unit, NetworkError> {
        return safeCall<Unit> {
            httpClient.post(
                urlString = "${getNGROKKey()}/add-exercise"
            ) {
                contentType(type = ContentType.Application.Json)
                header(key = "Authorization", value = "Bearer ${jwtUtil.tokenPair?.accessToken}")
                setBody(body = addExerciseDTO)
            }
        }
    }
}