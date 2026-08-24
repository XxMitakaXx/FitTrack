package com.example.fittrack.programs.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrack.core.data.util.onError
import com.example.fittrack.core.data.util.onSuccess
import com.example.fittrack.programs.domain.ProgramDataSource
import com.example.fittrack.programs.domain.models.enums.DaysPerWeek
import com.example.fittrack.programs.domain.models.enums.Equipment
import com.example.fittrack.programs.domain.models.enums.TrainingType
import com.example.fittrack.programs.presentation.util.toProgram
import com.example.fittrack.training.domain.models.dtos.ProgramDTO
import com.example.fittrack.training.domain.models.enums.TrainingLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.Uuid

class ProgramsViewModel(
    private val programDataSource: ProgramDataSource
): ViewModel() {

    private val selectedProgramId = MutableStateFlow<Uuid?>(value = null)
    private val selectedEquipmentFilters = MutableStateFlow<List<Equipment>>(value = emptyList())
    private val selectedDaysPerWeekFilters = MutableStateFlow<List<DaysPerWeek>>(value = emptyList())
    private val selectedTrainingLevelFilters = MutableStateFlow<List<TrainingLevel>>(value = emptyList())
    private val selectedTrainingTypeFilters = MutableStateFlow<List<TrainingType>>(value = emptyList())

    private val _state = MutableStateFlow(value = ProgramsState())
    val state = _state
        .onStart {
            observePrograms()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = ProgramsState()
        )

    fun onAction(action: ProgramsAction) {
        when (action) {
            is ProgramsAction.OnExploreButtonClick -> {
                makeExploreScreenVisible()
            }
            is ProgramsAction.OnLibraryButtonClick -> {
                makeLibraryScreenVisible()
            }
        }
    }

    private val filteredPrograms = fetchPrograms()
        .filterByEquipmentAndDaysPerWeekAndTrainingLevelAndTrainingType()
        .onEach { programDTOS ->
            _state.update { it.copy(
                hasRecordedPrograms = programDTOS.isNotEmpty(),
                isLoadingData = false
            ) }
        }
        .flowOn(context = Dispatchers.Default)

    private fun observePrograms() {
        viewModelScope.launch {
            filteredPrograms.collect { programDTOS ->
                val programs = programDTOS.map { programDTO -> programDTO.toProgram() }

                _state.update { it.copy(
                    programs = programs
                ) }
            }
        }
    }

    private fun fetchPrograms(): Flow<List<ProgramDTO>> {
        val programList = MutableStateFlow<List<ProgramDTO>>(value = emptyList())

        viewModelScope.launch {
            _state.update { it.copy(
                isLoadingData = true
            ) }

            programDataSource
                .observePrograms()
                .onSuccess { programs ->
                    _state.update { it.copy(
                        hasRecordedPrograms = programs.isNotEmpty(),
                        isLoadingData = false
                    ) }

                    programList.emit(value = programs)
                }
                .onError { networkError ->
                    _state.update { it.copy(
                        isLoadingData = false
                    ) }
                }
        }

        return programList
    }

    private fun Flow<List<ProgramDTO>>.filterByEquipmentAndDaysPerWeekAndTrainingLevelAndTrainingType(): Flow<List<ProgramDTO>> {
        return combine(
            flow = this,
            flow2 = selectedEquipmentFilters,
            flow3 = selectedDaysPerWeekFilters,
            flow4 = selectedTrainingLevelFilters,
            flow5 = selectedTrainingTypeFilters
        ) { programs, equipmentFilters, daysPerWeekFilters, trainingLevelFilters, trainingTypeFilters ->
            programs.filter { program ->
                val matchesEquipmentFilter =
                    equipmentFilters.isEmpty() || equipmentFilters.contains(
                        element = Equipment.valueOf(program.equipment)
                    )
                val matchesDaysPerWeekFilter =
                    daysPerWeekFilters.isEmpty() || daysPerWeekFilters.contains(
                        element = DaysPerWeek.valueOf(program.daysPerWeek)
                    )
                val matchesTrainingLevelFilter =
                    trainingLevelFilters.isEmpty() || trainingLevelFilters.contains(
                        element = TrainingLevel.valueOf(program.trainingLevel)
                    )
                val matchesTrainingTypeFilter =
                    trainingTypeFilters.isEmpty() || trainingTypeFilters.contains(
                        element = TrainingType.valueOf(program.trainingType)
                    )

                matchesEquipmentFilter && matchesDaysPerWeekFilter && matchesTrainingLevelFilter && matchesTrainingTypeFilter
            }
        }
    }

    private fun makeExploreScreenVisible() {
        _state.update {
            it.copy(
                isExploreScreenVisible = true
            )
        }
    }

    private fun makeLibraryScreenVisible() {
        _state.update {
            it.copy(
                isExploreScreenVisible = false
            )
        }
    }
}