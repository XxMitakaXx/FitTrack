package com.example.fittrack.training.domain.models.dtos

import kotlinx.serialization.Serializable

@Serializable
data class TrainingSetDTO(
    val id: String,
    val kilograms: Double,
    val reps: Int,
    val number: Int
)
