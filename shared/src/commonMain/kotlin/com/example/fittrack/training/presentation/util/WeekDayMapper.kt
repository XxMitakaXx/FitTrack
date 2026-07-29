package com.example.fittrack.training.presentation.util

import io.ktor.util.date.WeekDay

fun WeekDay.toOrdinalNumber(): Int {
    return when (this) {
        WeekDay.MONDAY -> 1
        WeekDay.TUESDAY -> 2
        WeekDay.WEDNESDAY -> 3
        WeekDay.THURSDAY -> 4
        WeekDay.FRIDAY -> 5
        WeekDay.SATURDAY -> 6
        WeekDay.SUNDAY -> 7
    }
}