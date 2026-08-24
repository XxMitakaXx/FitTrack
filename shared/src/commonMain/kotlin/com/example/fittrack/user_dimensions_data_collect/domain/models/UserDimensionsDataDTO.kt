package com.example.fittrack.user_dimensions_data_collect.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class UserDimensionsDataDTO(
    val height: Int,
    val age: Int,
    val gender: String
)
