package com.example.fittrack.body_weight.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fittrack.training.domain.models.ProgressBodyWeight

@Composable
fun ProgressWeightsHistory(
    progressBodyWeights: Map<String, List<ProgressBodyWeight>>,
    averageWeight: Double,
    onMonthListClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            count = progressBodyWeights.size,
            key = { index -> progressBodyWeights.keys.toList()[index] }
        ) {key ->
            progressBodyWeights.forEach { progressBodyWeightEntry ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = { onMonthListClick(key) }),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = progressBodyWeightEntry.key
                        )

                        Text(
                            text = "$averageWeight kg (${progressBodyWeightEntry.value.size})"
                        )
                    }

                    progressBodyWeightEntry.value.forEach { progressBodyWeightItem ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "${progressBodyWeightItem.recordedAt.dayOfWeek} ${progressBodyWeightItem.recordedAt.day}"
                            )

                            Text(
                                text = "${progressBodyWeightItem.weight} kg"
                            )
                        }
                    }
                }
            }
        }
    }
}