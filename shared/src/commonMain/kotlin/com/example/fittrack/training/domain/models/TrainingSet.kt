package com.example.fittrack.training.domain.models

import kotlin.uuid.Uuid

data class TrainingSet(
    val id: Uuid,
    val kilograms: Double,
    val reps: Int,
    val number: Int
)
