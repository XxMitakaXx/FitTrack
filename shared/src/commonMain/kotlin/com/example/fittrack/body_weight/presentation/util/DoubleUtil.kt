package com.example.fittrack.body_weight.presentation.util

fun Double.roundToDecimal(decimals: Int): Double {
    var multiplier = 1.0
    repeat(times = decimals) { multiplier *= 10 }
    return kotlin.math.round(x = this * multiplier) / multiplier
}

fun Float.roundToDecimal(decimals: Int): Float {
    var multiplier = 1.0f
    repeat(times = decimals) { multiplier *= 10 }
    return kotlin.math.round(x = this * multiplier) / multiplier
}