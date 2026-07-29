package com.example.fittrack.programs.presentation

sealed interface ProgramsAction {
    data object OnExploreButtonClick: ProgramsAction
    data object OnLibraryButtonClick: ProgramsAction
}