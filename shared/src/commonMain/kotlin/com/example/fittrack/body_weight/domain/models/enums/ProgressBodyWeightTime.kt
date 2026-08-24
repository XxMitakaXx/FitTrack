package com.example.fittrack.body_weight.domain.models.enums

enum class ProgressBodyWeightTime(
    val formatedText: String
) {
    WEEK(formatedText = "7D"),
    MONTH(formatedText = "30D"),
    THREE_MONTH(formatedText = "90D"),
    YEAR(formatedText = "1Y"),
    ALL(formatedText = "All")
}