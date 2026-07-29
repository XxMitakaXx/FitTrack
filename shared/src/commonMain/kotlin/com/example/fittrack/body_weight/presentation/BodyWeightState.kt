package com.example.fittrack.body_weight.presentation

import androidx.compose.runtime.Immutable
import com.example.fittrack.training.domain.models.ProgressBodyWeight
import com.example.fittrack.training.domain.models.enums.WeightDimension
import kotlinx.datetime.LocalDate

@Immutable
data class BodyWeightState(
    val currentWeight: Int = 0,
    val highestWeight: Int = 0,
    val lowestWeight: Int = 0,
    val progressBodyWeights: Map<String, List<ProgressBodyWeight>> = emptyMap(),
    val isPopupVisible: Boolean = false,
    val popupSelectedDate: LocalDate? = null,
    val popupSelectedWeight: String = "",
    val popupSelectedWeightDimension: WeightDimension = WeightDimension.KG
) {
    val averageWeight: Double = progressBodyWeights.values.flatMap { progressBodyWeights ->
        progressBodyWeights.map { progressBodyWeight ->
            progressBodyWeight.weight
        }
    }
        .average()
}
