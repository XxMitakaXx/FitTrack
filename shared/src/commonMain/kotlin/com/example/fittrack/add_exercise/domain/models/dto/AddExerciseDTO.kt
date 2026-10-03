package com.example.fittrack.add_exercise.domain.models.dto

import androidx.compose.ui.graphics.ImageBitmap
import com.example.fittrack.add_exercise.domain.models.enums.MuscleGroup
import kotlinx.serialization.Serializable

@Serializable
data class AddExerciseDTO(
    val image: String? = null,
    val name: String,
    val description: String,
    val targetMuscleGroup: String
)

