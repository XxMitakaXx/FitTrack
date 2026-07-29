package com.example.fittrack.training.domain.models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
data class ProgressBodyWeight(
    val id: Uuid,
    val weight: Int,
    val recordedAt: LocalDate
)