package com.example.fittrack.training.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.fittrack.training.presentation.util.centerItemIndex
import com.example.fittrack.training.presentation.util.getDaysInMonth
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month

@Composable
fun WheelDateSelector(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    val years = (1900..2050).toList()
    val months = Month.entries

    val initialYearIndex = years.indexOf(element = selectedDate.year).coerceAtLeast(minimumValue = 0)
    val initialMonthIndex = months.indexOf(element = selectedDate.month).coerceAtLeast(minimumValue = 0)

    val yearState = rememberLazyListState(initialFirstVisibleItemIndex = initialYearIndex)
    val monthState = rememberLazyListState(initialFirstVisibleItemIndex = initialMonthIndex)

    val currentYearIndex by remember { derivedStateOf { yearState.centerItemIndex() } }
    val currentMonthIndex by remember { derivedStateOf { monthState.centerItemIndex() } }

    val selectedYear = years.getOrElse(index = currentYearIndex) { selectedDate.year }
    val selectedMonth = months.getOrElse(index = currentMonthIndex) { selectedDate.month }

    val maxDays = getDaysInMonth(month = selectedMonth, year = selectedYear)
    val days = (1..maxDays).toList()

    val dayState = rememberLazyListState(initialFirstVisibleItemIndex = (selectedDate.day - 1).coerceIn(minimumValue = 0, maximumValue = maxDays - 1))
    val currentDayIndex by remember { derivedStateOf { dayState.centerItemIndex() } }


    LaunchedEffect(
        currentYearIndex,
        currentMonthIndex,
        currentDayIndex,
        maxDays
    ) {
        val year = years.getOrElse(index = currentYearIndex) { selectedYear }
        val month = months.getOrElse(index = currentMonthIndex) { selectedMonth }
        val day = days.getOrElse(index = currentDayIndex) { maxDays }

        val newDate = LocalDate(year = year, month =  month, day = day)
        if (newDate != null) {
            onDateSelected(newDate)
        }
    }

    Row(
        modifier = Modifier.fillMaxWidth().height(height = 50.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        WheelSlider(
            items = days.map { day -> day.toString() },
            state = dayState,
            modifier = Modifier.weight(weight = 1f)
        )

        WheelSlider(
            items = months.map { month -> month.name.lowercase().replaceFirstChar { char -> char.uppercaseChar() } },
            state = monthState,
            modifier = Modifier.weight(weight = 1.5f)
        )

        WheelSlider(
            items = years.map { year -> year.toString() },
            state = yearState,
            modifier = Modifier.weight(weight = 1f)
        )
    }
}