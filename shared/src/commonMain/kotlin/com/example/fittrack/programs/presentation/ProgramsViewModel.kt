package com.example.fittrack.programs.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrack.app.presentation.util.AppLogger
import com.example.fittrack.core.data.util.onError
import com.example.fittrack.core.data.util.onSuccess
import com.example.fittrack.programs.domain.ProgramDataSource
import com.example.fittrack.programs.domain.models.Program
import com.example.fittrack.programs.domain.models.enums.DaysPerWeek
import com.example.fittrack.programs.domain.models.enums.Equipment
import com.example.fittrack.programs.domain.models.enums.TrainingType
import com.example.fittrack.programs.presentation.util.toProgram
import com.example.fittrack.training.domain.models.enums.TrainingLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.Uuid

class ProgramsViewModel(
    private val programDataSource: ProgramDataSource,
    private val logger: AppLogger
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
            isAdmin()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = ProgramsState()
        )

    private val createdPrograms = fetchCreatedPrograms()
        .onEach {
            _state.update { it.copy(
                isLoadingData = false
            ) }
        }
        .flowOn(context = Dispatchers.Default)

    private val savedPrograms = fetchSavedPrograms()
        .onEach {
            _state.update { it.copy(
                isLoadingData = false
            ) }
        }
        .flowOn(context = Dispatchers.Default)


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

    private fun isAdmin() {
        viewModelScope.launch {
            programDataSource
                .observeIsAdmin()
                .onSuccess { isAdmin ->
                    _state.update { it.copy(
                        isAdmin = isAdmin
                    ) }
                }
        }

        logger.i(message = "isAdmin(): ${state.value.isAdmin}")
    }

    private fun observePrograms() {
        combine(
            flow = createdPrograms,
            flow2 = savedPrograms
        ) {
            createdPrograms, savedPrograms ->

            _state.update { it.copy(
                createdPrograms = createdPrograms,
                savedPrograms = savedPrograms
            ) }
        }
            .flowOn(context = Dispatchers.Default)
            .launchIn(scope = viewModelScope)
    }

    private fun fetchSavedPrograms(): Flow<List<Program>> {
        val programList = MutableStateFlow<List<Program>>(value = emptyList())

        viewModelScope.launch {
            _state.update { it.copy(
                isLoadingData = true
            ) }

            programDataSource
                .observeSavedPrograms()
                .onSuccess { programDTOs ->
                    _state.update { it.copy(
                        isLoadingData = false
                    ) }

                    val programs = programDTOs.map { programDTO -> programDTO.toProgram() }
                    programList.emit(value = programs)
                }
        }

        return programList
    }

    private fun fetchCreatedPrograms(): Flow<List<Program>> {
        val programList = MutableStateFlow<List<Program>>(value = emptyList())

        viewModelScope.launch {
            _state.update { it.copy(
                isLoadingData = true
            ) }

            programDataSource
                .observeCreatedPrograms()
                .onSuccess { programDTOs ->
                    _state.update { it.copy(
                        hasRecordedPrograms = programDTOs.isNotEmpty(),
                        isLoadingData = false
                    ) }

                    val programs = programDTOs.map { programDTOs -> programDTOs.toProgram() }
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

    private fun Flow<List<Program>>.filterByEquipmentAndDaysPerWeekAndTrainingLevelAndTrainingType(): Flow<List<Program>> {
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
                        element = program.equipment
                    )
                val matchesDaysPerWeekFilter =
                    daysPerWeekFilters.isEmpty() || daysPerWeekFilters.contains(
                        element = program.daysPerWeek
                    )
                val matchesTrainingLevelFilter =
                    trainingLevelFilters.isEmpty() || trainingLevelFilters.contains(
                        element = program.trainingLevel
                    )
                val matchesTrainingTypeFilter =
                    trainingTypeFilters.isEmpty() || trainingTypeFilters.contains(
                        element = program.trainingType
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