package com.example.fittrack.training.presentation.util

import androidx.compose.foundation.lazy.LazyListState
import kotlinx.datetime.Month
import kotlin.math.abs

fun getDaysInMonth(month: Month, year: Int): Int {
    val isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    return when(month) {
        Month.FEBRUARY -> if (isLeapYear) 29 else 28
        Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
        else -> 31
    }
}

fun LazyListState.centerItemIndex(): Int {
    val layoutInfo = this.layoutInfo
    val visibleItems = layoutInfo.visibleItemsInfo

    if (visibleItems.isEmpty()) {
        return this.firstVisibleItemIndex
    }

    val viewportCenter = (layoutInfo.viewportEndOffset - layoutInfo.viewportStartOffset) / 2

    val centerItem = visibleItems.minByOrNull {
        abs(n = it.offset + (it.size / 2) - viewportCenter)
    }

    return ((centerItem?.index ?: 1) -1).coerceAtLeast(minimumValue = 0)
}

fun isLeapYear(year: Int): Boolean {
    return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
}

fun getDaysInMonth(month: Int, year: Int): Int {
    return when (month) {
        4, 6, 9, 11 -> 30
        2 -> if (isLeapYear(year = year)) 29 else 28
        else -> 31
    }
}