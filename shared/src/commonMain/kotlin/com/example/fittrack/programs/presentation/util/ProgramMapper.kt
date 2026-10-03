package com.example.fittrack.programs.presentation.util

import com.example.fittrack.programs.domain.models.Program
import com.example.fittrack.programs.domain.models.Review
import com.example.fittrack.programs.domain.models.dtos.ReviewDTO
import com.example.fittrack.programs.domain.models.enums.AnyModification
import com.example.fittrack.programs.domain.models.enums.DaysPerWeek
import com.example.fittrack.programs.domain.models.enums.Equipment
import com.example.fittrack.programs.domain.models.enums.MuscleGainRate
import com.example.fittrack.programs.domain.models.enums.StrengthGainRate
import com.example.fittrack.programs.domain.models.enums.TrainingType
import com.example.fittrack.training.domain.models.Exercise
import com.example.fittrack.training.domain.models.Training
import com.example.fittrack.training.domain.models.TrainingDay
import com.example.fittrack.training.domain.models.TrainingSet
import com.example.fittrack.training.domain.models.TrainingWeek
import com.example.fittrack.training.domain.models.dtos.ExerciseDTO
import com.example.fittrack.training.domain.models.dtos.ProgramDTO
import com.example.fittrack.training.domain.models.dtos.TrainingDTO
import com.example.fittrack.training.domain.models.dtos.TrainingDayDTO
import com.example.fittrack.training.domain.models.dtos.TrainingSetDTO
import com.example.fittrack.training.domain.models.dtos.TrainingWeekDTO
import com.example.fittrack.training.domain.models.enums.TrainingLevel
import io.ktor.util.date.WeekDay
import kotlin.uuid.Uuid

fun ProgramDTO.toProgram(): Program {
    return Program(
        id = Uuid.parse(uuidString = this.id),
        name = this.name,
        creatorFullName = this.creatorFullName,
        creatorId = Uuid.parse(uuidString = this.creatorId),
        trainingWeeks = this.trainingWeeks.map { trainingWeekDTO -> trainingWeekDTO.toTrainingWeek() },
        trainingLevel = TrainingLevel.valueOf(value = this.trainingLevel),
        trainingType = TrainingType.valueOf(value = this.trainingType),
        daysPerWeek = DaysPerWeek.valueOf(value = this.daysPerWeek),
        recommendedDays = this.recommendedDays.map { recommendedDay -> WeekDay.from(value = recommendedDay) },
        timePerWorkoutMinutes = this.timePerWorkoutMinutes,
        totalCountUsed = this.totalCountUsed,
        rate = this.rate,
        equipment = Equipment.valueOf(value = this.equipment),
        imageUrl = this.imageUrl,
        reviews = this.reviews.map { reviewDTO -> reviewDTO.toReview() },
        isPublic = this.isPublic
    )
}

private fun TrainingWeekDTO.toTrainingWeek(): TrainingWeek {
    return TrainingWeek(
        trainingWeekId = Uuid.parse(uuidString = this.trainingWeekId),
        number = this.number,
        trainingDays = this.trainingDays.map { trainingDayDTO -> trainingDayDTO.toTrainingDay() }
    )
}

private fun TrainingDayDTO.toTrainingDay(): TrainingDay {
    return TrainingDay(
        trainingDayId = Uuid.parse(uuidString = this.trainingDayId),
        weekDay = WeekDay.from(value = this.weekDay),
        trainings = this.trainings.map { trainingDTO -> trainingDTO.toTraining() }
    )
}

private fun TrainingDTO.toTraining(): Training {
    return Training(
        trainingId = Uuid.parse(uuidString = this.trainingId),
        exercises = this.exercises.map { exerciseDTO -> exerciseDTO.toExercise() }
    )
}

private fun ExerciseDTO.toExercise(): Exercise {
    return Exercise(
        exerciseId = Uuid.parse(uuidString = this.exerciseId),
        name = this.name,
        pictureUrl = this.pictureUrl,
        targetReps = this.targetReps,
        targetKg = this.targetKg,
        trainingSets = this.sets.map { trainingSetDTO -> trainingSetDTO.toTrainingSet() },
    )
}

private fun TrainingSetDTO.toTrainingSet(): TrainingSet {
    return TrainingSet(
        id = Uuid.parse(uuidString = this.id),
        kilograms = this.kilograms,
        reps = this.reps,
        number = this.number
    )
}

private fun ReviewDTO.toReview(): Review {
    return Review(
        id = Uuid.parse(uuidString = this.id),
        userFullName = this.userFullName,
        stars = this.stars,
        strengthGainRate = StrengthGainRate.valueOf(value = this.strengthGainRate),
        muscleGainRate = MuscleGainRate.valueOf(value = this.muscleGainRate),
        anyModification = AnyModification.valueOf(value = this.anyModification),
        review = this.review
    )
}