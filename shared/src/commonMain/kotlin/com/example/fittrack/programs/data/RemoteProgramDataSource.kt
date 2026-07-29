package com.example.fittrack.programs.data

import com.example.fittrack.app.data.jwt.JWTUtil
import com.example.fittrack.core.data.getApiKey
import com.example.fittrack.core.data.safeCall
import com.example.fittrack.core.data.util.NetworkError
import com.example.fittrack.core.data.util.Result
import com.example.fittrack.programs.domain.ProgramDataSource
import com.example.fittrack.training.domain.models.dtos.ProgramDTO
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header

class RemoteProgramDataSource(
    private val httpClient: HttpClient,
    private val jwtUtil: JWTUtil
): ProgramDataSource {

    override suspend fun observePrograms(): Result<List<ProgramDTO>, NetworkError> {
        return safeCall<List<ProgramDTO>> {
            httpClient.get(
                urlString = "${getApiKey()}/programs"
            ) {
                header(key = "Authorization", value = "Bearer ${jwtUtil.tokenPair?.accessToken}")
            }
        }
    }
}