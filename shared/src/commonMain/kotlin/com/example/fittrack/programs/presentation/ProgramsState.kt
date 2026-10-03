package com.example.fittrack.programs.presentation

import com.example.fittrack.programs.domain.models.Program
import com.example.fittrack.programs.presentation.util.SelectedProgramPage

data class ProgramsState(
    val hasRecordedPrograms: Boolean = false,
    val isLoadingData: Boolean = false,
    val isExploreScreenVisible: Boolean = true,
    val createdPrograms: List<Program> = emptyList(),
    val savedPrograms: List<Program> = emptyList(),
    val selectedProgramPage: SelectedProgramPage = SelectedProgramPage.PRIVATE,
    val isAdmin: Boolean = false
)
