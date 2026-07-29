package com.example.fittrack.training.domain.models

import kotlin.uuid.Uuid

data class TrainingWeek(
    val trainingWeekId: Uuid,
    val number: Int,
    val trainingDays: List<TrainingDay>
)
