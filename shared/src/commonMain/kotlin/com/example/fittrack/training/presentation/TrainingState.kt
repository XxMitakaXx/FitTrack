package com.example.fittrack.training.presentation

import androidx.compose.runtime.Immutable
import com.example.fittrack.training.domain.models.UserTrainingData
import com.example.fittrack.training.domain.models.enums.Gender
import com.example.fittrack.training.domain.models.enums.WeightDimension
import kotlinx.datetime.LocalDate

@Immutable
data class TrainingState(
    val isTrainScreenVisible: Boolean = true,
    val hasRecordedPrograms: Boolean = false,
    val isLoadingData: Boolean = false,
    val userTrainingData: UserTrainingData? = null,
    val userProgressInPercentage: Float = 0.0f,
    val weight: String = "",
    val height: String = "",
    val age: String = "",
    val gender: String = Gender.NON_SPECIFIED.toString(),
    val isPopupVisible: Boolean = false,
    val epoch: Long? = 0,
    val popupSelectedDate: LocalDate? = null,
    val popupSelectedWeight: String = "",
    val popupSelectedWeightDimension: WeightDimension = WeightDimension.KG
)
