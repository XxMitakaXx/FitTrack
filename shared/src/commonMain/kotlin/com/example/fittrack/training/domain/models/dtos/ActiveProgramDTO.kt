package com.example.fittrack.training.domain.models.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ActiveProgramDTO(
    val id: String,
    val name: String,
    val trainingWeeksDTOs: List<TrainingWeekDTO>,
    val trainingLevel: String,
    val trainingType: String,
    val daysPerWeek: String,
    val recommendedDays: List<String>,
    val timePerWorkoutMinutes: Int,
    val totalCountUsed: Int,
    val rate: Double,
    val equipment: String,
    val userProgressDTO: UserProgressDTO
)