package com.example.fittrack.training.domain.models.dtos

import kotlinx.serialization.Serializable

@Serializable
data class TrainingDTO(
    val trainingId: String,
    val exercises: List<ExerciseDTO>
)
