package com.example.fittrack.body_weight.presentation.util

import com.example.fittrack.app.navigation.NavigationRoute
import com.example.fittrack.training.domain.models.ProgressBodyWeight
import kotlinx.serialization.json.Json

fun NavigationRoute.BodyWeightProgressScreen.toProgressBodyWeights(): List<ProgressBodyWeight> {
    val progressBodyWeights: List<ProgressBodyWeight> = Json.decodeFromString(this.progressBodyWeightsJson)
    return progressBodyWeights
}