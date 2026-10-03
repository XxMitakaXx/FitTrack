package com.example.fittrack.add_exercise.presentation

import androidx.compose.ui.graphics.ImageBitmap
import com.example.fittrack.add_exercise.domain.models.enums.MuscleGroup

sealed interface AddExerciseVarietyAction {
    data class OnImageValueChange(val imageBitmap: ImageBitmap): AddExerciseVarietyAction
    data class OnNameValueChange(val name: String): AddExerciseVarietyAction
    data class OnDescriptionValueChange(val description: String): AddExerciseVarietyAction
    data class OnTargetMuscleGroupValueChange(val targetMuscleGroup: MuscleGroup): AddExerciseVarietyAction
    data object OnTargetMuscleGroupDropDownToggle: AddExerciseVarietyAction
    data object OnSaveExercise: AddExerciseVarietyAction
}