package com.example.fittrack.training.domain.models

import kotlin.uuid.Uuid

data class Training(
    val trainingId: Uuid,
    val exercises: List<Exercise>
)