package com.example.fittrack.add_exercise.domain

import com.example.fittrack.add_exercise.domain.models.dto.AddExerciseDTO
import com.example.fittrack.core.data.util.NetworkError
import com.example.fittrack.core.data.util.Result

interface AddExerciseDataSource {
    suspend fun saveExercise(addExerciseDTO: AddExerciseDTO): Result<Unit, NetworkError>
}