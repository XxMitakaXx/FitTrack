package com.example.fittrack.training.presentation.util

import kotlin.math.roundToInt

fun lbsToKg(lbs: Int): Int {
    return (lbs * 0.45359237).roundToInt()
}