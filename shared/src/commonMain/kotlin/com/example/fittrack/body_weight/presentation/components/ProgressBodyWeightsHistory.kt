package com.example.fittrack.body_weight.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fittrack.body_weight.presentation.util.roundToDecimal
import com.example.fittrack.training.domain.models.ProgressBodyWeight
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.history
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProgressBodyWeightsHistory(
    progressBodyWeightsEntries: Map<String, List<ProgressBodyWeight>>,
    unfoldedMonthWeightProgressesKeys: List<String>,
    onMonthListClick: (String) -> Unit,
    onProgressBodyWeightItemDelete: (String) -> Unit,
) {
    Column {
        Spacer(modifier = Modifier.height(height = 10.dp))

        Text(
            text = stringResource(resource = Res.string.history),
            fontSize = 25.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(all = 16.dp)
        )

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 3.dp)
                .background(color = MaterialTheme.colorScheme.surface)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            progressBodyWeightsEntries.forEach { progressBodyWeightsEntry ->
                val dateString = progressBodyWeightsEntry.key
                val progressBodyWeightItems = progressBodyWeightsEntry.value
                val itemAverageWeightForTheMonth = progressBodyWeightItems
                    .map { progressBodyWeight -> progressBodyWeight.weight }
                    .average()
                    .roundToDecimal(decimals = 2)

                ProgressWeightHistoryItem(
                    dateString = dateString,
                    progressBodyWeightItems = progressBodyWeightItems,
                    itemAverageWeightForTheMonth = itemAverageWeightForTheMonth,
                    unfoldedMonthWeightProgressesKeys = unfoldedMonthWeightProgressesKeys,
                    onMonthListClick = onMonthListClick,
                    onProgressBodyWeightItemDelete = onProgressBodyWeightItemDelete
                )
            }
        }
    }
}