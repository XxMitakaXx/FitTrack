package com.example.fittrack.programs.domain.models

import com.example.fittrack.programs.domain.models.enums.AnyModification
import com.example.fittrack.programs.domain.models.enums.MuscleGainRate
import com.example.fittrack.programs.domain.models.enums.StrengthGainRate
import kotlin.uuid.Uuid

data class Review(
    val id: Uuid,
    val userFullName: String,
    val stars: Int,
    val strengthGainRate: StrengthGainRate,
    val muscleGainRate: MuscleGainRate,
    val anyModification: AnyModification,
    val review: String
)
