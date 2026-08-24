package com.example.fittrack.training.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrack.app.presentation.util.AppLogger
import com.example.fittrack.core.data.util.onError
import com.example.fittrack.core.data.util.onSuccess
import com.example.fittrack.training.domain.UserTrainingDataSource
import com.example.fittrack.training.domain.models.UserTrainingData
import com.example.fittrack.training.domain.models.dtos.ProgressBodyWeightDTO
import com.example.fittrack.training.domain.models.enums.WeightDimension
import com.example.fittrack.training.presentation.util.lbsToKg
import com.example.fittrack.training.presentation.util.toUserTrainingData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class TrainingViewModel(
    private val userTrainingDataSource: UserTrainingDataSource,
    private val logger: AppLogger
): ViewModel() {
    private val _state = MutableStateFlow(value = TrainingState())
    val state = _state
        .onStart {
            observeUserTrainingData()
        }
        .stateIn(
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 0L),
            scope = viewModelScope,
            initialValue = TrainingState()
        )

    private val _events = Channel<TrainingEvent>()
    val events = _events.receiveAsFlow()

    private val userTrainingData = fetchUserData()
        .flowOn(context = Dispatchers.Default)

    private val stringsFlow = MutableStateFlow(value = "")

    fun onAction(action: TrainingAction) {
        when(action) {
            is TrainingAction.OnTrainButtonClick -> makeTrainScreenVisible()
            is TrainingAction.OnHistoryButtonClick -> makeHistoryScreenVisible()
            is TrainingAction.OnNavigateToProgramsScreen -> navigateToProgramsScreen()
            is TrainingAction.OnNavigateToAddUserTrainingDataScreen -> navigateToAddUserTrainingDataScreen()
            is TrainingAction.OnSelectedPopupDate -> onSelectedPopupDate(localDate = action.localDate)
            is TrainingAction.OnSelectedPopupWeight -> onSelectedPopupWeight(weight = action.weight)
            is TrainingAction.OnSelectedPopupWeightDimensions -> onSelectedPopupWeightDimensions(weightDimension = action.weightDimension)
            is TrainingAction.OnMakePopupVisible -> makePopupVisible()
            is TrainingAction.OnMakePopupNotVisible -> makePopupNotVisible()
            is TrainingAction.OnSaveBodyWeight -> saveBodyWeight()
            is TrainingAction.OnNavigateToProgressBodyWightScreen -> navigateToProgressBodyWeightScreen()
        }
    }

    private fun navigateToProgressBodyWeightScreen() {
        val progressBodyWeights = _state.value.userTrainingData?.userStats?.progressBodyWeights

        if (progressBodyWeights != null) {
            viewModelScope.launch {
                _events.send(
                    element = TrainingEvent.OnNavigateToProgressBodyWeightScreen(
                        progressBodyWeights = progressBodyWeights
                    )
                )
            }
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

    private fun makePopupNotVisible() {
        viewModelScope.launch {
            _state.update { it.copy(
                isPopupVisible = false
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

    private fun onSelectedPopupWeightDimensions(weightDimension: WeightDimension) {
        viewModelScope.launch {
            _state.update { it.copy(
                popupSelectedWeightDimension = weightDimension
            ) }
        }
    }

    private fun onSelectedPopupWeight(weight: String) {
        viewModelScope.launch {
            _state.update { it.copy(
                popupSelectedWeight = weight
            ) }
        }
    }

    private fun onSelectedPopupDate(localDate: LocalDate) {
        viewModelScope.launch {
            _state.update { it.copy(
                popupSelectedDate = localDate
            ) }
        }
    }

    private fun navigateToProgramsScreen() {
        viewModelScope.launch {
            _events.send(element = TrainingEvent.OnNavigateToProgramsScreen)
        }
    }

    private fun navigateToAddUserTrainingDataScreen() {
        viewModelScope.launch {
            _events.send(element = TrainingEvent.OnNavigateToAddUserTrainingDataScreen)
        }
    }

    private fun observeUserTrainingData() {
        combine(
            flow = userTrainingData,
            flow2 = stringsFlow
        ) { userTrainingData, _ ->
            _state.update { it.copy(
                userTrainingData = userTrainingData
            ) }

            calculateProgress()
        }
            .flowOn(context = Dispatchers.Default)
            .launchIn(scope = viewModelScope)
    }

    private fun fetchUserData(): Flow<UserTrainingData> {
        logger.i(message = "fetchUserData(): In")
        val userTrainingData = MutableStateFlow(value = UserTrainingData())
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

                    logger.i(message = "userTrainingDataDTO: $userTrainingDataDTO")

                    userTrainingData.emit(value = userTrainingDataDTO.toUserTrainingData())
                }
                .onError { networkError ->
                    _state.update { it.copy(
                        isLoadingData = false
                    ) }
                }
        }

        return userTrainingData
    }

    private fun calculateProgress() {
        var programDays = 0
        _state.value.userTrainingData?.activeProgram?.trainingWeeks?.forEach {
            programDays += it.trainingDays.size
        }

        val userProgressInWeek = _state.value.userTrainingData?.activeProgram?.userProgress?.week
        val userProgressInDay = _state.value.userTrainingData?.activeProgram?.userProgress?.day

        userProgressInWeek?.let {
            userProgressInDay?.let {
                val userProgressInPercentage = (((userProgressInWeek - 1 * 7) + userProgressInDay) / programDays).toFloat()

                viewModelScope.launch {
                    _state.update { it.copy(
                        userProgressInPercentage = userProgressInPercentage
                    ) }
                }
            }
        }
    }

    private fun makeTrainScreenVisible() {
        viewModelScope.launch {
            _state.update { it.copy(
                isTrainScreenVisible = true
            ) }
        }
    }

    private fun makeHistoryScreenVisible() {
        viewModelScope.launch {
            _state.update { it.copy(
                isTrainScreenVisible = false
            ) }
        }
    }
}