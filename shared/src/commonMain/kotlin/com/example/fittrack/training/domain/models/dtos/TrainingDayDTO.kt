package com.example.fittrack.training.domain.models.dtos

import kotlinx.serialization.Serializable

@Serializable
data class TrainingDayDTO(
    val trainingDayId: String,
    val weekDay: String,
    val trainings: List<TrainingDTO>
)
