package com.example.fittrack.training.domain.models.dtos

import kotlinx.serialization.Serializable

@Serializable
data class UserProgressDTO(
    val week: Int,
    val day: Int
)