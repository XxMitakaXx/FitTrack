package com.example.fittrack.training.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fittrack.training.presentation.util.getDaysInMonth
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kotlin.time.Clock

@Composable
fun DatePickerSelectorContainer(
    onSelectedPopupDate: (localDate: LocalDate) -> Unit
) {
    val today = Clock.System.todayIn(timeZone = TimeZone.currentSystemDefault())

    val years = (today.year - 50)..(today.year + 10)
    val months = Month.entries
    val days = 1..31

    val dayState = rememberLazyListState(initialFirstVisibleItemIndex = today.day - 1)
    val monthState = rememberLazyListState(initialFirstVisibleItemIndex = today.month.number - 1)
    val yearState = rememberLazyListState(initialFirstVisibleItemIndex = years.indexOf(element = today.year))

    val selectedDay by remember { derivedStateOf { dayState.firstVisibleItemIndex + 1 } }
    val selectedMonth by remember { derivedStateOf { monthState.firstVisibleItemIndex + 1 } }
    val selectedMonthName by remember { derivedStateOf { Month.entries[monthState.firstVisibleItemIndex] } }
    val selectedYear by remember { derivedStateOf { years.elementAt(index = yearState.firstVisibleItemIndex)  } }

    val maxDays by remember(key1 = selectedMonth, selectedYear) {
        derivedStateOf { getDaysInMonth(month = selectedMonth, year = selectedYear) }
    }

    val isScrolling by remember {
        derivedStateOf {
            dayState.isScrollInProgress || monthState.isScrollInProgress || yearState.isScrollInProgress
        }
    }

    LaunchedEffect(key1 = isScrolling) {
        if (!isScrolling) {
            if (dayState.firstVisibleItemIndex >= maxDays) {
                dayState.animateScrollToItem(index = maxDays - 1)
            }

            val selectedDate = LocalDate(year = selectedYear, month = selectedMonth, day = selectedDay)
            if (selectedDate > today) {
                dayState.animateScrollToItem(index = today.day - 1)
                monthState.animateScrollToItem(index = today.month.number - 1)
                yearState.animateScrollToItem(index = years.indexOf(element = today.year))
            } else {
                onSelectedPopupDate(selectedDate)
            }
        }
    }

    Column(
        modifier = Modifier.padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BodyWeightRollerColumn(
                state = dayState,
                items = days.toList()
            )

            BodyWeightRollerColumn(
                state = monthState,
                items = months
            )

            BodyWeightRollerColumn(
                state = yearState,
                items = years.toList()
            )
        }

        Text(
            text = "$selectedDay ${selectedMonthName.name.lowercase().replaceFirstChar { char -> char.uppercaseChar() }} $selectedYear",
            modifier = Modifier.padding(bottom = 24.dp)
        )
    }
}