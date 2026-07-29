package com.example.fittrack.programs.domain.models

import com.example.fittrack.programs.domain.models.enums.DaysPerWeek
import com.example.fittrack.programs.domain.models.enums.Equipment
import com.example.fittrack.programs.domain.models.enums.TrainingType
import com.example.fittrack.training.domain.models.TrainingWeek
import com.example.fittrack.training.domain.models.enums.TrainingLevel
import io.ktor.util.date.WeekDay
import kotlin.uuid.Uuid

data class Program(
    val id: Uuid,
    val name: String,
    val creatorFullName: String,
    val creatorId: Uuid,
    val trainingWeeks: List<TrainingWeek>,
    val trainingLevel: TrainingLevel,
    val trainingType: TrainingType,
    val daysPerWeek: DaysPerWeek,
    val recommendedDays: List<WeekDay>,
    val timePerWorkoutMinutes: Int,
    val totalCountUsed: Int,
    val rate: Double,
    val equipment: Equipment,
    val reviews: List<Review>
)
