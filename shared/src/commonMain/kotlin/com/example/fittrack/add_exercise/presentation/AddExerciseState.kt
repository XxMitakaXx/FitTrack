package com.example.fittrack.add_exercise.presentation

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.ImageBitmap
import com.example.fittrack.add_exercise.domain.models.enums.MuscleGroup

@Immutable
data class AddExerciseState(
    val image: ImageBitmap? = null,
    val name: String = "",
    val description: String = "",
    val targetMuscleGroup: MuscleGroup = MuscleGroup.OTHER,
    val isDropDownMenuExpanded: Boolean = false,
    val isExerciseNameExisting: Boolean = false
)
