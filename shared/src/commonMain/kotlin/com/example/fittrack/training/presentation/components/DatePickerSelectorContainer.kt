package com.example.fittrack.training.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

@Composable
fun DatePickerSelectorContainer(
    onSelectedPopupDate: (localDate: LocalDate) -> Unit
) {
    var currentDate by remember {
        mutableStateOf(value = Clock.System.now().toLocalDateTime(timeZone = TimeZone.currentSystemDefault()).date)
    }

    LaunchedEffect(key1 = currentDate) {
        onSelectedPopupDate(currentDate)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(all = 24.dp)
            .background(color = MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        WheelDateSelector(
            selectedDate = currentDate,
            onDateSelected = { newDate ->
                currentDate = newDate
            }
        )

        Spacer(modifier = Modifier.height(height = 32.dp))

        Text(
            text = "${currentDate.day} ${currentDate.month} ${currentDate.year}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}