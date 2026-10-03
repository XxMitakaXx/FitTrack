package com.example.fittrack.programs.domain

import com.example.fittrack.core.data.util.NetworkError
import com.example.fittrack.core.data.util.Result
import com.example.fittrack.training.domain.models.dtos.ProgramDTO

interface ProgramDataSource {
    suspend fun observeCreatedPrograms(): Result<List<ProgramDTO>, NetworkError>
    suspend fun observeSavedPrograms(): Result<List<ProgramDTO>, NetworkError>
    suspend fun observeIsAdmin(): Result<Boolean, NetworkError>
}