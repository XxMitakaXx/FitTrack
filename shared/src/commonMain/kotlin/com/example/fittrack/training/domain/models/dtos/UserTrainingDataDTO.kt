package com.example.fittrack.training.domain.models.dtos

import kotlinx.serialization.Serializable

@Serializable
data class UserTrainingDataDTO(
    val activeProgramDTO: ActiveProgramDTO?,
    val userStatsDTO: UserStatsDTO?
)