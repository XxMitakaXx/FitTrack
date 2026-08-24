package com.example.fittrack.body_weight.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.fittrack.app.navigation.NavigationRoute
import com.example.fittrack.app.presentation.util.AppLogger
import com.example.fittrack.body_weight.domain.models.enums.ProgressBodyWeightTime
import com.example.fittrack.body_weight.presentation.util.toProgressBodyWeights
import com.example.fittrack.core.data.util.onError
import com.example.fittrack.core.data.util.onSuccess
import com.example.fittrack.training.domain.UserTrainingDataSource
import com.example.fittrack.training.domain.models.ProgressBodyWeight
import com.example.fittrack.training.domain.models.dtos.ProgressBodyWeightDTO
import com.example.fittrack.training.domain.models.enums.WeightDimension
import com.example.fittrack.training.presentation.util.lbsToKg
import com.example.fittrack.training.presentation.util.toProgressBodyWeightDTO
import com.example.fittrack.training.presentation.util.toUserTrainingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.minus
import kotlinx.datetime.todayIn
import kotlin.collections.groupBy
import kotlin.time.Clock

class BodyWeightViewModel(
    savedStateHandle: SavedStateHandle,
    private val logger: AppLogger,
    private val userTrainingDataSource: UserTrainingDataSource
): ViewModel() {
    private val selectedTimeFilter = MutableStateFlow(value = ProgressBodyWeightTime.ALL)
    private val _state = MutableStateFlow(value = BodyWeightState())
    val state = _state
        .onStart {
            observeProgressBodyWeights()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = BodyWeightState()
        )

    private val _events = Channel<BodyWeightEvent>()
    val events = _events.receiveAsFlow()

    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    private var progressBodyWeights = refreshTrigger
        .flatMapLatest {
            fetchProgressBodyWeights()
                .filterByTime()
        }
        .flowOn(context = Dispatchers.Default)

    fun onAction(action: BodyWeightAction) {
        when(action) {
            is BodyWeightAction.OnMakePopupVisible -> makePopupVisible()
            is BodyWeightAction.OnMakePopupNotVisible -> makePopupNotVisible()
            is BodyWeightAction.OnSelectedPopupDate -> editPopupDate(localDate = action.localDate)
            is BodyWeightAction.OnSelectedPopupWeight -> editWeight(weight = action.weight)
            is BodyWeightAction.OnSelectedPopupWeightDimensions -> editWeightDimensions(action.weightDimension)
            is BodyWeightAction.OnSaveBodyWeight -> saveBodyWeight()
            is BodyWeightAction.OnMonthListClick -> onUnfoldedMonthWeightProgressesClick(action.dateString)
            is BodyWeightAction.OnProgressBodyWeightItemDelete -> onProgressBodyWeightItemDelete(action.dateString)
            is BodyWeightAction.OnProgressBodyWeightTimeChange -> selectedTimeFilterChange(progressBodyWeightTime = action.progressBodyWeightTime)
        }
    }

    init {
        refreshProgressBodyWeights()
    }

    private fun selectedTimeFilterChange(progressBodyWeightTime: ProgressBodyWeightTime) {
        selectedTimeFilter.value = progressBodyWeightTime

        _state.update { it.copy(
            selectedTimeFilter = progressBodyWeightTime
        ) }
    }

    private fun refreshProgressBodyWeights() {
        refreshTrigger.tryEmit(value = Unit)
    }

    private fun fetchProgressBodyWeights(): Flow<List<ProgressBodyWeight>> {
        val progressBodyWeights = MutableStateFlow<List<ProgressBodyWeight>>(value = emptyList())
        viewModelScope.launch {
            _state.update { it.copy(
                isLoadingData = true
            ) }

            userTrainingDataSource
                .fetchUserTrainingData()
                .onSuccess { userTrainingDataDTO ->
                    _state.update { it.copy(
                        isLoadingData = false
                    ) }
                    val fetchedProgressBodyWeighs = userTrainingDataDTO
                        .toUserTrainingData()
                        .userStats
                        ?.progressBodyWeights ?: emptyList()

                    progressBodyWeights.emit(value = fetchedProgressBodyWeighs)
                }
                .onError { networkError ->
                    _state.update { it.copy(
                        isLoadingData = false
                    ) }
                }
        }

        return progressBodyWeights
    }

    private fun onProgressBodyWeightItemDelete(dateString: String) {
        _state.value.progressBodyWeights.forEach { progressBodyWeightsMap ->
            progressBodyWeightsMap.value.forEach { progressBodyWeight ->
                if (progressBodyWeight.recordedAt.toString() == dateString) {
                    viewModelScope.launch {
                        userTrainingDataSource.deleteUserBodyWeight(progressBodyWeightDTO = progressBodyWeight.toProgressBodyWeightDTO())
                            .onSuccess {
                                refreshProgressBodyWeights()
                            }
                            .onError { networkError ->

                            }
                    }
                }
            }
        }
    }

    private fun onUnfoldedMonthWeightProgressesClick(dateString: String) {
        if (dateString in _state.value.unfoldedMonthWeightProgressesKeys) {
            removeUnfoldedMonthWeightProgresses(dateString = dateString)
        } else {
            addUnfoldedMonthWeightProgresses(dateString = dateString)
        }
    }

    private fun removeUnfoldedMonthWeightProgresses(dateString: String) {
        viewModelScope.launch {
            _state.update { it.copy(
                unfoldedMonthWeightProgressesKeys = _state.value.unfoldedMonthWeightProgressesKeys - dateString
            ) }
        }
    }

    private fun addUnfoldedMonthWeightProgresses(dateString: String) {
        viewModelScope.launch {
            _state.update { it.copy(
                unfoldedMonthWeightProgressesKeys = _state.value.unfoldedMonthWeightProgressesKeys + dateString
            ) }
        }
    }

    private fun saveBodyWeight() {
        val weightInKg: Int = if (_state.value.popupSelectedWeightDimension == WeightDimension.LBS) {
            lbsToKg(lbs = _state.value.popupSelectedWeight.toInt())
        } else {
            _state.value.popupSelectedWeight.toInt()
        }

        val progressBodyWeightDTO = ProgressBodyWeightDTO(
            weight = weightInKg,
            recordedAt = _state.value.popupSelectedDate!!.toString()
        )

        viewModelScope.launch {
            userTrainingDataSource.saveUserBodyWeight(progressBodyWeightDTO = progressBodyWeightDTO)
                .onSuccess {
                    makePopupNotVisible()
                    refreshProgressBodyWeights()
                }
                .onError {

                }
        }
    }

    private fun editWeightDimensions(weightDimension: WeightDimension) {
        viewModelScope.launch {
            _state.update { it.copy(
                popupSelectedWeightDimension = weightDimension
            ) }
        }
    }

    private fun editWeight(weight: String) {
        viewModelScope.launch {
            _state.update { it.copy(
                popupSelectedWeight = weight
            ) }
        }
    }

    private fun editPopupDate(localDate: LocalDate) {
        viewModelScope.launch {
            _state.update { it.copy(
                popupSelectedDate = localDate
            ) }
        }
    }

    private fun makePopupVisible() {
        viewModelScope.launch {
            _state.update { it.copy(
                isPopupVisible = true
            ) }
        }
    }

    private fun makePopupNotVisible() {
        viewModelScope.launch {
            _state.update { it.copy(
                isPopupVisible = false
            ) }
        }
    }

    private fun observeProgressBodyWeights() {
        combine(
            flow = progressBodyWeights,
            flow2 = selectedTimeFilter
        ) { progressBodyWeights, selectedTimeFilter ->
            progressBodyWeights
        }
            .groupByRelativeDate()
            .onEach { groupedProgressBodyWeights ->
                _state.update { it.copy(
                    progressBodyWeights = groupedProgressBodyWeights
                ) }
            }
            .flowOn(context = Dispatchers.Default)
            .launchIn(scope = viewModelScope)
    }

    private fun Flow<List<ProgressBodyWeight>>.filterByTime(): Flow<List<ProgressBodyWeight>> {
        return combine(
            flow = this,
            flow2 = selectedTimeFilter
        ) { progressBodyWeights, selectedTimeFilter ->
            logger.i("before filter -> .filterByTime(): ${progressBodyWeights.size}")
            progressBodyWeights
                .filter { progressBodyWeight ->
                    selectedTimeFilter == ProgressBodyWeightTime.ALL || isDataInRange(date = progressBodyWeight.recordedAt)
                }
        }
    }

    private fun isDataInRange(date: LocalDate): Boolean {
        val today = Clock.System.todayIn(timeZone = TimeZone.currentSystemDefault())
        return when(selectedTimeFilter.value) {
            ProgressBodyWeightTime.WEEK -> {
                val lastWeek = today.minus(value = 1, unit = DateTimeUnit.WEEK)
                date in lastWeek..today
            }
            ProgressBodyWeightTime.MONTH -> {
                val lastMonth = today.minus(value = 1, unit = DateTimeUnit.MONTH)
                date in lastMonth..today
            }
            ProgressBodyWeightTime.THREE_MONTH -> {
                val lastThreeMonths = today.minus(value = 3, unit = DateTimeUnit.MONTH)
                date in lastThreeMonths..today
            }
            ProgressBodyWeightTime.YEAR -> {
                val lastYear = today.minus(value = 1, unit = DateTimeUnit.YEAR)
                date in lastYear..today
            }
            else -> true
        }
    }

    private fun Flow<List<ProgressBodyWeight>>.groupByRelativeDate(): Flow<Map<String, List<ProgressBodyWeight>>> {
        val formatter = LocalDate.Format {
            year()
            char(' ')
            monthName(names = MonthNames.ENGLISH_FULL)
        }
        return map { progressBodyWeights ->
            progressBodyWeights
                .sortedByDescending { it.recordedAt }
                .groupBy { it.recordedAt.format(format = formatter) }
                .mapValues { (_, progressBodyWeights) ->
                    progressBodyWeights.sortedBy { it.recordedAt }
                }
        }
    }
}
