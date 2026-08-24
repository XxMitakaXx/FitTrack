package com.example.fittrack.body_weight.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fittrack.body_weight.presentation.BodyWeightAction
import com.example.fittrack.body_weight.presentation.util.roundToDecimal
import com.example.fittrack.training.domain.models.ProgressBodyWeight
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.arrow_back
import fittrack.shared.generated.resources.arrow_down
import fittrack.shared.generated.resources.arrow_right
import fittrack.shared.generated.resources.delete
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProgressWeightHistoryItem(
    dateString: String,
    progressBodyWeightItems: List<ProgressBodyWeight>,
    itemAverageWeightForTheMonth: Double,
    unfoldedMonthWeightProgressesKeys: List<String>,
    onMonthListClick: (String) -> Unit,
    onProgressBodyWeightItemDelete: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { onMonthListClick(dateString) })
                .padding(all = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                Text(
                    text = dateString
                )

                if (dateString in unfoldedMonthWeightProgressesKeys) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = stringResource(resource = Res.string.arrow_down)
                    )
                } else {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = stringResource(resource = Res.string.arrow_right)
                    )
                }
            }

            Text(
                text = "~ $itemAverageWeightForTheMonth kg (${progressBodyWeightItems.size})"
            )
        }

        if (dateString in unfoldedMonthWeightProgressesKeys) {
            progressBodyWeightItems.forEach { progressBodyWeightItem ->
                ProgressBodyWeightSwipeableDropDownItem(
                    isRevealed = false,
                    actions = {
                        IconButton(
                            onClick = { onProgressBodyWeightItemDelete(progressBodyWeightItem.recordedAt.toString()) },
                            modifier = Modifier.background(color = Color.Red)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = stringResource(resource = Res.string.delete)
                            )
                        }
                    },
                    content = {
                        BodyWeightHistoryDropdownItem(
                            progressBodyWeightItem = progressBodyWeightItem
                        )
                    }
                )
            }
        }
    }
}