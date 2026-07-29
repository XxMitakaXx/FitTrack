package com.example.fittrack.training.domain.models.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ProgressPhotoDTO(
    val photoUrl: String,
    val createdAt: String
)