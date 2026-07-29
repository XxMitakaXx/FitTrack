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
import io.ktor.util.Hash.combine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
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
import kotlinx.datetime.format.char
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import kotlin.collections.groupBy
import kotlin.time.Clock

class BodyWeightViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val logger: AppLogger,
    private val userTrainingDataSource: UserTrainingDataSource,
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

    private val route = savedStateHandle.toRoute<NavigationRoute.BodyWeightProgressScreen>()
    private val progressBodyWeights = flowOf(value = route.toProgressBodyWeights())
        .filterByTime()

    fun onAction(action: BodyWeightAction) {
        when(action) {
            is BodyWeightAction.OnMakePopupVisible -> makePopupVisible()
            is BodyWeightAction.OnMakePopupNotVisible -> makePopupNotVisible()
            is BodyWeightAction.OnSelectedPopupDate -> editPopupDate(localDate = action.localDate)
            is BodyWeightAction.OnSelectedPopupWeight -> editWeight(weight = action.weight)
            is BodyWeightAction.OnSelectedPopupWeightDimensions -> editWeightDimensions(action.weightDimension)
            is BodyWeightAction.OnSaveBodyWeight -> saveBodyWeight()
            is BodyWeightAction.OnMonthListClick -> {}
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
            char('/')
            monthNumber()
            char('/')
            day()
        }
        return map { progressBodyWeights ->
            progressBodyWeights
                .groupBy { progressBodyWeight ->
                    progressBodyWeight.recordedAt
                }
                .mapValues { (_, progressBodyWeights) ->
                    progressBodyWeights.sortedBy { progressBodyWeight -> progressBodyWeight.recordedAt}
                }
                .toList()
                .sortedByDescending { it.first }
                .toMap()
                .mapKeys { (date, _) ->
                    date.format(format = formatter)
                }
        }
    }
}
