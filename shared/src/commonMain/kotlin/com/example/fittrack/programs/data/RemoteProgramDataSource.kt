package com.example.fittrack.programs.data

import com.example.fittrack.app.data.jwt.JWTUtil
import com.example.fittrack.core.data.getApiKey
import com.example.fittrack.core.data.getNGROKKey
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

    override suspend fun observeCreatedPrograms(): Result<List<ProgramDTO>, NetworkError> {
        return safeCall<List<ProgramDTO>> {
            httpClient.get(
                urlString = "${getNGROKKey()}/programs/get_created_programs"
            ) {
                header(key = "Authorization", value = "Bearer ${jwtUtil.tokenPair?.accessToken}")
            }
        }
    }

    override suspend fun observeSavedPrograms(): Result<List<ProgramDTO>, NetworkError> {
        return safeCall<List<ProgramDTO>> {
            httpClient.get(
                urlString = "${getNGROKKey()}/programs/get_saved_programs"
            ) {
                header(key = "Authorization", value = "Bearer ${jwtUtil.tokenPair?.accessToken}")
            }
        }
    }

    override suspend fun observeIsAdmin(): Result<Boolean, NetworkError> {
        return safeCall<Boolean> {
            httpClient.get(
                urlString = "${getNGROKKey()}/users/is_admin"
            ) {
                header(key = "Authorization", value = "Bearer ${jwtUtil.tokenPair?.accessToken}")
            }
        }
    }
}