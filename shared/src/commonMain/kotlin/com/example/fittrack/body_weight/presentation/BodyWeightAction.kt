package com.example.fittrack.body_weight.presentation

import com.example.fittrack.body_weight.domain.models.enums.ProgressBodyWeightTime
import com.example.fittrack.training.domain.models.enums.WeightDimension
import kotlinx.datetime.LocalDate

sealed interface BodyWeightAction {
    data object OnMakePopupVisible: BodyWeightAction
    data object OnMakePopupNotVisible: BodyWeightAction
    data class OnSelectedPopupDate(val localDate: LocalDate): BodyWeightAction
    data class OnSelectedPopupWeight(val weight: String): BodyWeightAction
    data class OnSelectedPopupWeightDimensions(val weightDimension: WeightDimension): BodyWeightAction
    data object OnSaveBodyWeight: BodyWeightAction
    data class OnMonthListClick(val dateString: String): BodyWeightAction
    data class OnProgressBodyWeightItemDelete(val dateString: String): BodyWeightAction
    data class OnProgressBodyWeightTimeChange(val progressBodyWeightTime: ProgressBodyWeightTime): BodyWeightAction
}