package com.example.fittrack.add_exercise.presentation

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrack.add_exercise.domain.AddExerciseDataSource
import com.example.fittrack.add_exercise.domain.models.dto.AddExerciseDTO
import com.example.fittrack.add_exercise.domain.models.enums.MuscleGroup
import com.example.fittrack.app.presentation.util.AppLogger
import com.example.fittrack.core.data.util.NetworkError
import com.example.fittrack.core.data.util.onError
import com.example.fittrack.core.data.util.onSuccess
import com.example.fittrack.core.presentation.toByteArray
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.io.encoding.Base64

class AddExerciseViewModel(
    private val addExerciseDataSource: AddExerciseDataSource,
    private val logger: AppLogger
): ViewModel() {
    private val _state = MutableStateFlow(value = AddExerciseState())
    val state = _state
        .onStart {

        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = AddExerciseState()
        )

    private val _events = Channel<AddExerciseEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: AddExerciseVarietyAction) {
        when(action) {
            is AddExerciseVarietyAction.OnImageValueChange -> editImage(bitmapImage = action.imageBitmap)
            is AddExerciseVarietyAction.OnNameValueChange -> editName(name = action.name)
            is AddExerciseVarietyAction.OnDescriptionValueChange -> editDescription(description = action.description)
            is AddExerciseVarietyAction.OnTargetMuscleGroupValueChange -> editTargetMuscleGroup(targetMuscleGroup = action.targetMuscleGroup)
            is AddExerciseVarietyAction.OnTargetMuscleGroupDropDownToggle -> toggleTargetMuscleGroupDropDown()
            is AddExerciseVarietyAction.OnSaveExercise -> saveExercise()
        }
    }

    private fun saveExercise() {
        viewModelScope.launch {
            val rawByteArray = _state.value.image?.toByteArray(quality = 100) ?: throw Exception(
                "AddExerciseViewModel() saveExercise(): ByteArray is null"
            )

            val byteArrayEncodedString = Base64.encode(source = rawByteArray)

            val addExerciseDTO = AddExerciseDTO(
                image = byteArrayEncodedString,
                name = _state.value.name,
                description = _state.value.description,
                targetMuscleGroup = _state.value.targetMuscleGroup.name
            )

            addExerciseDataSource
                .saveExercise(addExerciseDTO = addExerciseDTO)
                .onSuccess {
                    _events.send(element = AddExerciseEvent.OnExerciseSave)
                }
                .onError { e ->
                    when(e) {
                        NetworkError.SERVER_ERROR -> {
                            _state.update { it.copy(
                                isExerciseNameExisting = true
                            ) }
                        }
                        else -> Unit
                    }

                    logger.e(message = "AddExerciseViewModel() saveExercise(): Exception: ${e.name}")
                }
        }
    }

    private fun toggleTargetMuscleGroupDropDown() {
        viewModelScope.launch {
            _state.update { it.copy(
                isDropDownMenuExpanded = !_state.value.isDropDownMenuExpanded
            ) }
        }
    }

    private fun editTargetMuscleGroup(targetMuscleGroup: MuscleGroup) {
        viewModelScope.launch {
            _state.update { it.copy(
                targetMuscleGroup = targetMuscleGroup
            ) }
        }

        toggleTargetMuscleGroupDropDown()
    }

    private fun editDescription(description: String) {
        viewModelScope.launch {
            _state.update { it.copy(
                description = description
            ) }
        }
    }

    private fun editName(name: String) {
        viewModelScope.launch {
            _state.update { it.copy(
                name = name
            ) }
        }
    }

    private fun editImage(bitmapImage: ImageBitmap) {
        logger.i(message = "AddExerciseViewModel() editImage(): image height: ${bitmapImage.height} and width ${bitmapImage.width}")
        _state.update { it.copy(
            image = bitmapImage
        ) }
    }
}