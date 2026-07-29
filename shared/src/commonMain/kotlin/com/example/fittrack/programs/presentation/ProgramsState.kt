package com.example.fittrack.programs.presentation

import com.example.fittrack.programs.domain.models.Program

data class ProgramsState(
    val hasRecordedPrograms: Boolean = false,
    val isLoadingData: Boolean = false,
    val isExploreScreenVisible: Boolean = true,
    val programs: List<Program> = emptyList()
)
