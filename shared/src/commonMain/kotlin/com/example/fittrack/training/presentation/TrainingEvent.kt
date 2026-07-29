package com.example.fittrack.training.presentation

import com.example.fittrack.training.domain.models.ProgressBodyWeight

sealed interface TrainingEvent {
    data object OnNavigateToProgramsScreen: TrainingEvent
    data object OnNavigateToAddUserTrainingDataScreen: TrainingEvent
    data object OnUserTrainingDataSaved: TrainingEvent
    data class OnNavigateToProgressBodyWeightScreen(val progressBodyWeights: List<ProgressBodyWeight>): TrainingEvent
}