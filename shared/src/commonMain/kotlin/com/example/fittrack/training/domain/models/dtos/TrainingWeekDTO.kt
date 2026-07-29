package com.example.fittrack.training.domain.models.dtos

import kotlinx.serialization.Serializable

@Serializable
data class TrainingWeekDTO(
    val trainingWeekId: String,
    val number: Int,
    val trainingDays: List<TrainingDayDTO>
)
