package com.example.fittrack.training.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.fittrack.body_weight.presentation.BodyWeightState
import com.example.fittrack.training.domain.models.enums.WeightDimension
import com.example.fittrack.training.presentation.TrainingState
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.weight
import org.jetbrains.compose.resources.stringResource

@Composable
fun BodyWeightSelector(
    state: TrainingState,
    onSelectedPopupWeight: (String) -> Unit,
    onSelectedWeightDimension: (WeightDimension) -> Unit
) {
    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = state.popupSelectedWeight,
            onValueChange = { onSelectedPopupWeight(it) },
            singleLine = true,
            label = { Text(text = stringResource(resource = Res.string.weight)) },
            modifier = Modifier
                .padding(all = 10.dp)
                .width(width = 150.dp)
                .focusRequester(focusRequester = focusRequester),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(size = 16.dp)
        )

        Spacer(modifier = Modifier.width(width = 10.dp))

        WeightDimensionSelector(
            selectedDimension = state.popupSelectedWeightDimension,
            onSelectedWeightDimension = { onSelectedWeightDimension(it) }
        )
    }
}

@Composable
fun BodyWeightSelector(
    state: BodyWeightState,
    onSelectedPopupWeight: (String) -> Unit,
    onSelectedWeightDimension: (WeightDimension) -> Unit
) {
    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = state.popupSelectedWeight,
            onValueChange = { onSelectedPopupWeight(it) },
            singleLine = true,
            label = { Text(text = stringResource(resource = Res.string.weight)) },
            modifier = Modifier
                .padding(all = 10.dp)
                .width(width = 150.dp)
                .focusRequester(focusRequester = focusRequester),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(size = 16.dp)
        )

        Spacer(modifier = Modifier.width(width = 10.dp))

        WeightDimensionSelector(
            selectedDimension = state.popupSelectedWeightDimension,
            onSelectedWeightDimension = { onSelectedWeightDimension(it) }
        )
    }
}
