package com.example.fittrack.training.presentation

import com.example.fittrack.training.domain.models.enums.WeightDimension
import kotlinx.datetime.LocalDate

sealed interface TrainingAction {
    data object OnTrainButtonClick: TrainingAction
    data object OnHistoryButtonClick: TrainingAction
    data object OnNavigateToProgramsScreen: TrainingAction
    data object OnNavigateToAddUserTrainingDataScreen: TrainingAction
    data object OnUserTrainingDataSave: TrainingAction
    data class OnSelectedPopupDate(val localDate: LocalDate): TrainingAction
    data class OnSelectedPopupWeight(val weight: String): TrainingAction
    data class OnSelectedPopupWeightDimensions(val weightDimension: WeightDimension): TrainingAction
    data object OnMakePopupVisible: TrainingAction
    data object OnMakePopupNotVisible: TrainingAction
    data object OnSaveBodyWeight: TrainingAction
    data object OnNavigateToProgressBodyWightScreen: TrainingAction
}