package com.example.fittrack.training.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fittrack.body_weight.presentation.BodyWeightAction
import com.example.fittrack.body_weight.presentation.BodyWeightState
import com.example.fittrack.training.presentation.TrainingAction
import com.example.fittrack.training.presentation.TrainingState
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.log_bodyweight
import fittrack.shared.generated.resources.save
import org.jetbrains.compose.resources.stringResource

@Composable
fun AddWeightPopupContent(
    state: TrainingState,
    onAction: (TrainingAction) -> Unit
) {
    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(size = 16.dp)
            )
            .padding(all = 32.dp),
//            .fillMaxSize()
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(resource = Res.string.log_bodyweight),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(height = 30.dp))

        DatePickerSelectorContainer(
            onSelectedPopupDate = { localDate ->
                onAction(TrainingAction.OnSelectedPopupDate(localDate = localDate))
            }
        )

        Spacer(modifier = Modifier.height(height = 20.dp))

        BodyWeightSelector(
            state = state,
            onSelectedPopupWeight = { weight ->
                onAction(TrainingAction.OnSelectedPopupWeight(weight = weight))
            },
            onSelectedWeightDimension = { weightDimension ->
                onAction(TrainingAction.OnSelectedPopupWeightDimensions(weightDimension = weightDimension))
            }
        )

        Spacer(modifier = Modifier.height(height = 50.dp))

        TextButton(
            onClick = { onAction(TrainingAction.OnSaveBodyWeight) },
            modifier = Modifier.width(width = 130.dp),
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = Color.Unspecified,
                disabledContentColor = Color.Unspecified
            )
        ) {
            Text(
                text = stringResource(resource = Res.string.save),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun AddWeightPopupContent(
    state: BodyWeightState,
    onAction: (BodyWeightAction) -> Unit
) {
    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(size = 16.dp)
            )
            .padding(all = 32.dp),
//            .fillMaxSize()
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(resource = Res.string.log_bodyweight),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(height = 30.dp))

        DatePickerSelectorContainer(
            onSelectedPopupDate = { localDate ->
                onAction(BodyWeightAction.OnSelectedPopupDate(localDate = localDate))
            }
        )

        Spacer(modifier = Modifier.height(height = 20.dp))

        BodyWeightSelector(
            state = state,
            onSelectedPopupWeight = { weight ->
                onAction(BodyWeightAction.OnSelectedPopupWeight(weight = weight))
            },
            onSelectedWeightDimension = { weightDimension ->
                onAction(BodyWeightAction.OnSelectedPopupWeightDimensions(weightDimension = weightDimension))
            }
        )

        Spacer(modifier = Modifier.height(height = 50.dp))

        TextButton(
            onClick = { onAction(BodyWeightAction.OnSaveBodyWeight) },
            modifier = Modifier.width(width = 130.dp),
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = Color.Unspecified,
                disabledContentColor = Color.Unspecified
            )
        ) {
            Text(
                text = stringResource(resource = Res.string.save),
                fontSize = 16.sp
            )
        }
    }
}

@Preview
@Composable
fun AddWeightPopupContentPreview() {
    AddWeightPopupContent(
        state = TrainingState(),
        onAction = {}
    )
}