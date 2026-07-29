package com.example.fittrack.programs.domain

import com.example.fittrack.core.data.util.NetworkError
import com.example.fittrack.core.data.util.Result
import com.example.fittrack.training.domain.models.dtos.ProgramDTO

interface ProgramDataSource {
    suspend fun observePrograms(): Result<List<ProgramDTO>, NetworkError>
}