package com.example.fittrack.training.domain.models.dtos

import kotlinx.serialization.Serializable

@Serializable
data class UserStatsDTO(
    val id: String,
    val weightKg: Int,
    val heightCm: Int,
    val gender: String,
    val age: Int,
    val lifetimeWorkouts: Int,
    val lifetimeLiftedKg: Double,
    val lifetimeTrainingHours: Int,
    val lifetimePRs: Int,
    val progressPhotosDTOs: List<ProgressPhotoDTO>,
    val progressBodyWeightDTOS: List<ProgressBodyWeightDTO>
)