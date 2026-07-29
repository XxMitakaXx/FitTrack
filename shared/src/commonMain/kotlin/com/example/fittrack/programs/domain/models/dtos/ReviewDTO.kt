package com.example.fittrack.programs.domain.models.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ReviewDTO(
    val id: String,
    val userFullName: String,
    val stars: Int,
    val strengthGainRate: String,
    val muscleGainRate: String,
    val anyModification: String,
    val review: String
)
