package com.example.fittrack.training.domain.models

import com.example.fittrack.programs.domain.models.enums.DaysPerWeek
import com.example.fittrack.programs.domain.models.enums.Equipment
import com.example.fittrack.programs.domain.models.enums.TrainingType
import com.example.fittrack.training.domain.models.enums.TrainingLevel
import io.ktor.util.date.WeekDay
import kotlin.uuid.Uuid

data class ActiveProgram(
    val id: Uuid,
    val name: String,
    val trainingWeeks: List<TrainingWeek>,
    val trainingLevel: TrainingLevel,
    val trainingType: TrainingType,
    val daysPerWeek: DaysPerWeek,
    val recommendedDays: List<WeekDay>,
    val timePerWorkoutMinutes: Int,
    val totalCountUsed: Int,
    val rate: Double,
    val equipment: Equipment,
    val userProgress: UserProgress
)
