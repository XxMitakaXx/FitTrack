package com.example.fittrack.training.domain.models

import com.example.fittrack.training.domain.models.enums.Gender
import kotlin.uuid.Uuid

data class UserStats(
    val id: Uuid,
    val weightKg: Int,
    val heightCm: Int,
    val gender: Gender,
    val age: Int,
    val lifetimeWorkouts: Int,
    val lifetimeLiftedKg: Double,
    val lifetimeTrainingHours: Int,
    val lifetimePRs: Int,
    val progressPhotos: List<ProgressPhoto>,
    val progressBodyWeights: List<ProgressBodyWeight>
)
