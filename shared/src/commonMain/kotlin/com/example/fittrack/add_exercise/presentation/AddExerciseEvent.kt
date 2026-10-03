package com.example.fittrack.add_exercise.presentation

sealed interface AddExerciseEvent {
    data object OnExerciseSave: AddExerciseEvent
}