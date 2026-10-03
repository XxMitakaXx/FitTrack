package com.example.fittrack.training.domain.models.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseDTO(
    val exerciseId: String,
    val name: String,
    val pictureUrl: String,
    val targetReps: Int,
    val targetKg: Double,
    val sets: List<TrainingSetDTO>
)
