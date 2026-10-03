package com.example.fittrack.training.domain.models

import kotlin.uuid.Uuid

data class Exercise(
    val exerciseId: Uuid,
    val name: String,
    val pictureUrl: String,
    val targetReps: Int,
    val targetKg: Double,
    val trainingSets: List<TrainingSet>
)
