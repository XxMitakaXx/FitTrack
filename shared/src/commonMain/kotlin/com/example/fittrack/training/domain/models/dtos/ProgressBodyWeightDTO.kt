package com.example.fittrack.training.domain.models.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ProgressBodyWeightDTO(
    val id: String? = null,
    val weight: Int,
    val recordedAt: String
)
