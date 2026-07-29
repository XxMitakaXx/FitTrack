package com.example.fittrack.training.domain.models

import io.ktor.util.date.WeekDay
import kotlin.uuid.Uuid

data class TrainingDay(
    val trainingDayId: Uuid,
    val weekDay: WeekDay,
    val trainings: List<Training>
)
