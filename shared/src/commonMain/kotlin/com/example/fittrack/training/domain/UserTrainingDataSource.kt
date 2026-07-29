package com.example.fittrack.training.domain

import com.example.fittrack.core.data.util.NetworkError
import com.example.fittrack.core.data.util.Result
import com.example.fittrack.training.domain.models.dtos.ProgressBodyWeightDTO
import com.example.fittrack.training.domain.models.dtos.UserTrainingDataDTO

interface UserTrainingDataSource {
    suspend fun fetchUserTrainingData(): Result<UserTrainingDataDTO, NetworkError>
    suspend fun saveUserBodyWeight(progressBodyWeightDTO: ProgressBodyWeightDTO): Result<Unit, NetworkError>
}