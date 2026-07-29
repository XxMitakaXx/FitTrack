package com.example.fittrack.user_dimensions_data_collect.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrack.app.presentation.util.AppLogger
import com.example.fittrack.core.data.util.onError
import com.example.fittrack.core.data.util.onSuccess
import com.example.fittrack.training.domain.models.enums.Gender
import com.example.fittrack.user_dimensions_data_collect.domain.UserDimensionsDataSource
import com.example.fittrack.user_dimensions_data_collect.domain.models.UserDimensionsDataDTO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserDimensionsDataCollectViewModel(
    private val userDimensionsDataSource: UserDimensionsDataSource,
    private val logger: AppLogger
): ViewModel() {
    private val _state = MutableStateFlow(value = UserDimensionsDataCollectState())
    val state = _state
        .onStart {  }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = UserDimensionsDataCollectState()
        )

    private val _events = Channel<UserDimensionsDataCollectEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: UserDimensionsDataCollectAction) {
        when(action) {
            is UserDimensionsDataCollectAction.OnWeightValueChange -> editWeight(weight = action.weight)
            is UserDimensionsDataCollectAction.OnHeightValueChange -> editHeight(height = action.height)
            is UserDimensionsDataCollectAction.OnAgeValueChange -> editAge(age = action.age)
            is UserDimensionsDataCollectAction.OnGenderValueChange -> editGender(gender = action.gender)
            is UserDimensionsDataCollectAction.OnSave -> saveUserDimensionsData()
        }
    }

    private fun saveUserDimensionsData() {
        logger.i(message = "saveUserDimensionsData(): In")

        val userDimensionsDataDTO = UserDimensionsDataDTO(
            weight = _state.value.weight.toInt(),
            height = _state.value.height.toInt(),
            age = _state.value.age.toInt(),
            gender = _state.value.gender
        )

        viewModelScope.launch {
            userDimensionsDataSource.saveUserDimensionsData(userDimensionsDataDTO = userDimensionsDataDTO)
                .onSuccess {
                    logger.i(message = "saveUserDimensionsData(): Successful saving user dimensions data.")
                    _events.send(element = UserDimensionsDataCollectEvent.OnSavedUserDimensionsDataSuccess)
                }
                .onError {
                    logger.e(message = "saveUserDimensionsData(): Not successful saving user dimensions data.")
                }
        }
    }

    private fun editAge(age: String) {
        viewModelScope.launch {
            _state.update { it.copy(
                age = age
            ) }
        }
    }

    private fun editGender(gender: String) {
        viewModelScope.launch {
            _state.update { it.copy(
                gender = gender
            ) }
        }
    }

    private fun editHeight(height: String) {
        viewModelScope.launch {
            _state.update { it.copy(
                height = height
            ) }
        }
    }

    private fun editWeight(weight: String) {
        viewModelScope.launch {
            _state.update { it.copy(
                weight = weight
            ) }
        }
    }


}