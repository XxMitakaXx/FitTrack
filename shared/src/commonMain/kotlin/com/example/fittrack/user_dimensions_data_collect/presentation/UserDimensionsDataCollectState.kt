package com.example.fittrack.user_dimensions_data_collect.presentation

import androidx.compose.runtime.Immutable
import com.example.fittrack.training.domain.models.enums.Gender

@Immutable
data class UserDimensionsDataCollectState(
    val weight: String = "",
    val height: String = "",
    val age: String = "",
    val gender: String = Gender.NON_SPECIFIED.toString()
)
