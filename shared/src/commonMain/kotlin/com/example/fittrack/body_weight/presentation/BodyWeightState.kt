package com.example.fittrack.body_weight.presentation

import androidx.compose.runtime.Immutable
import com.example.fittrack.body_weight.domain.models.enums.ProgressBodyWeightTime
import com.example.fittrack.body_weight.presentation.util.roundToDecimal
import com.example.fittrack.training.domain.models.ProgressBodyWeight
import com.example.fittrack.training.domain.models.enums.WeightDimension
import kotlinx.datetime.LocalDate

@Immutable
data class BodyWeightState(
    val progressBodyWeights: Map<String, List<ProgressBodyWeight>> = emptyMap(),
    val isPopupVisible: Boolean = false,
    val popupSelectedDate: LocalDate? = null,
    val popupSelectedWeight: String = "",
    val popupSelectedWeightDimension: WeightDimension = WeightDimension.KG,
    val selectedTimeFilter: ProgressBodyWeightTime = ProgressBodyWeightTime.ALL,
    val unfoldedMonthWeightProgressesKeys: List<String> = emptyList(),
    val isLoadingData: Boolean = false
) {
    val averageBodyWeight: Double = if (progressBodyWeights.isNotEmpty()) {
        progressBodyWeights.values.flatMap { progressBodyWeights ->
        progressBodyWeights.map { progressBodyWeight ->
            progressBodyWeight.weight
        }
    }
        .average()
        .roundToDecimal(decimals = 2)
    } else {
        0.00
    }

    val currentBodyWeight: Int = if (progressBodyWeights.isNotEmpty()) {
        progressBodyWeights.values
            .flatten()
            .maxBy { progressBodyWeight -> progressBodyWeight.recordedAt }.weight
    } else {
        0
    }

    val highestBodyWeight: Int = if (progressBodyWeights.isNotEmpty()) {
        progressBodyWeights.values
            .flatten()
            .maxOf { progressBodyWeight -> progressBodyWeight.weight }
    } else {
        0
    }

    val lowesBodyWeight: Int = if (progressBodyWeights.isNotEmpty()) {
        progressBodyWeights.values
            .flatten()
            .minOf { progressBodyWeight -> progressBodyWeight.weight }
    } else {
        0
    }

    val averageBodyWeightStartDate: LocalDate = if (progressBodyWeights.isNotEmpty()) {
        progressBodyWeights.values.flatMap { progressBodyWeights ->
            progressBodyWeights.sortedBy { progressBodyWeight ->
                progressBodyWeight.recordedAt
            }
        }.last().recordedAt
    } else {
        LocalDate.fromEpochDays(0)
    }

    val averageBodyWeightEndDate: LocalDate = if (progressBodyWeights.isNotEmpty()) {
        progressBodyWeights.values.flatMap { progressBodyWeights ->
            progressBodyWeights.sortedBy { progressBodyWeight ->
                progressBodyWeight.recordedAt
            }
        }.first().recordedAt
    } else {
        LocalDate.fromEpochDays(0)
    }

    val changeBodyWeight: Int = if (progressBodyWeights.isNotEmpty()) {
        val latestBodyWeight = progressBodyWeights.values.flatMap { progressBodyWeights ->
            progressBodyWeights.sortedBy { progressBodyWeight ->
                progressBodyWeight.weight
            }
        }.first().weight

        val newestBodyWeight = progressBodyWeights.values.flatMap { progressBodyWeights ->
            progressBodyWeights.sortedBy { progressBodyWeight ->
                progressBodyWeight.weight
            }
        }.last().weight

        latestBodyWeight - newestBodyWeight
    } else {
        0
    }
}
