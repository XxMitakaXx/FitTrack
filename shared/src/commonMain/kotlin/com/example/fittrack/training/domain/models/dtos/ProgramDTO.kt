package com.example.fittrack.training.domain.models.dtos

import com.example.fittrack.programs.domain.models.dtos.ReviewDTO
import kotlinx.serialization.Serializable

@Serializable
data class ProgramDTO(
    val id: String,
    val name: String,
    val creatorId: String,
    val creatorFullName: String,
    val totalCountUsed: Int,
    val trainingWeeks: List<TrainingWeekDTO>,
    val trainingLevel: String,
    val trainingType: String,
    val daysPerWeek: String,
    val recommendedDays: List<String>,
    val timePerWorkoutMinutes: Int,
    val rate: Double,
    val equipment: String,
    val reviews: List<ReviewDTO>
)
