package com.example.fittrack.training.presentation.util

import com.example.fittrack.programs.domain.models.enums.DaysPerWeek
import com.example.fittrack.programs.domain.models.enums.Equipment
import com.example.fittrack.programs.domain.models.enums.TrainingType
import com.example.fittrack.training.domain.models.ActiveProgram
import com.example.fittrack.training.domain.models.Exercise
import com.example.fittrack.training.domain.models.ProgressPhoto
import com.example.fittrack.training.domain.models.ProgressBodyWeight
import com.example.fittrack.training.domain.models.Training
import com.example.fittrack.training.domain.models.TrainingDay
import com.example.fittrack.training.domain.models.TrainingSet
import com.example.fittrack.training.domain.models.TrainingWeek
import com.example.fittrack.training.domain.models.UserProgress
import com.example.fittrack.training.domain.models.UserStats
import com.example.fittrack.training.domain.models.UserTrainingData
import com.example.fittrack.training.domain.models.dtos.ActiveProgramDTO
import com.example.fittrack.training.domain.models.dtos.ExerciseDTO
import com.example.fittrack.training.domain.models.dtos.ProgressPhotoDTO
import com.example.fittrack.training.domain.models.dtos.ProgressBodyWeightDTO
import com.example.fittrack.training.domain.models.dtos.TrainingSetDTO
import com.example.fittrack.training.domain.models.dtos.TrainingDTO
import com.example.fittrack.training.domain.models.dtos.TrainingDayDTO
import com.example.fittrack.training.domain.models.dtos.TrainingWeekDTO
import com.example.fittrack.training.domain.models.dtos.UserProgressDTO
import com.example.fittrack.training.domain.models.dtos.UserStatsDTO
import com.example.fittrack.training.domain.models.dtos.UserTrainingDataDTO
import com.example.fittrack.training.domain.models.enums.Gender
import com.example.fittrack.training.domain.models.enums.TrainingLevel
import io.ktor.util.date.WeekDay
import kotlinx.datetime.LocalDate
import kotlin.uuid.Uuid

fun UserTrainingDataDTO.toUserTrainingData(): UserTrainingData {
    return UserTrainingData(
        activeProgram = activeProgramDTO?.toActiveProgram(),
        userStats = userStatsDTO?.toUserStats()
    )
}

private fun UserStatsDTO.toUserStats(): UserStats {
    return UserStats(
        id = Uuid.parse(uuidString = this.id),
        weightKg = this.weightKg,
        heightCm = this.heightCm,
        gender = Gender.valueOf(value = this.gender),
        age = this.age,
        lifetimeWorkouts = this.lifetimeWorkouts,
        lifetimeLiftedKg = this.lifetimeLiftedKg,
        lifetimeTrainingHours = this.lifetimeTrainingHours,
        lifetimePRs = this.lifetimePRs,
        progressPhotos = this.progressPhotosDTOs.map { progressPhotoDTO -> progressPhotoDTO.toProgressPhoto() },
        progressBodyWeights = this.progressBodyWeightDTOS.map { progressWeightDTO -> progressWeightDTO.toProgressWeight() }
    )
}

fun ProgressBodyWeightDTO.toProgressWeight(): ProgressBodyWeight {
    return ProgressBodyWeight(
        id = this.id?.let { Uuid.parse(uuidString = it) } as Uuid,
        weight = this.weight,
        recordedAt = LocalDate.parse(input = this.recordedAt)
    )
}

fun ProgressBodyWeight.toProgressBodyWeightDTO(): ProgressBodyWeightDTO {
    return ProgressBodyWeightDTO(
        id = this.id.toString(),
        weight = this.weight,
        recordedAt = this.recordedAt.toString()
    )
}

private fun ProgressPhotoDTO.toProgressPhoto(): ProgressPhoto {
    return ProgressPhoto(
        photoUrl = this.photoUrl,
        createdAt = this.createdAt
    )
}

private fun ActiveProgramDTO.toActiveProgram(): ActiveProgram {
    return ActiveProgram(
        id = Uuid.parse(uuidString = this.id),
        name = this.name,
        trainingWeeks = this.trainingWeeksDTOs.map { trainingWeekDTO -> trainingWeekDTO.toTrainingWeek() },
        trainingLevel = TrainingLevel.valueOf(value = this.trainingLevel),
        trainingType = TrainingType.valueOf(this.trainingType),
        daysPerWeek = DaysPerWeek.valueOf(this.daysPerWeek),
        recommendedDays = this.recommendedDays.map { recommendedDay -> WeekDay.from(recommendedDay) },
        timePerWorkoutMinutes = this.timePerWorkoutMinutes,
        totalCountUsed = this.totalCountUsed,
        rate = this.rate,
        equipment = Equipment.valueOf(this.equipment),
        userProgress = this.userProgressDTO.toUserProgress()
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

private fun UserProgressDTO.toUserProgress(): UserProgress {
    return UserProgress(
        week = this.week,
        day = this.day
    )
}