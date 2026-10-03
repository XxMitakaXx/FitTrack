package com.example.fittrack.add_exercise.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Panorama
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fittrack.add_exercise.domain.models.enums.MuscleGroup
import com.example.fittrack.add_exercise.presentation.util.rememberImagePicker
import com.example.fittrack.app.presentation.util.rememberGalleryPermissionManager
import com.example.fittrack.core.presentation.ObserveAsEvent
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.add
import fittrack.shared.generated.resources.add_exercise
import fittrack.shared.generated.resources.description
import fittrack.shared.generated.resources.exercise_image
import fittrack.shared.generated.resources.exercise_with_that_name_already_exists
import fittrack.shared.generated.resources.name
import fittrack.shared.generated.resources.request_permission_for_photo_library
import fittrack.shared.generated.resources.upload_image
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AddExerciseRoot(
    viewModel: AddExerciseViewModel = koinViewModel(),
    onNavigateToProgramScreen: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(events = viewModel.events) { event ->
        when(event) {
            is AddExerciseEvent.OnExerciseSave -> { onNavigateToProgramScreen() }
            else -> Unit
        }
    }

    AddExerciseScreen(
        state = state,
        onAction = { action ->
            viewModel.onAction(action = action)
        }
    )
}

@Composable
fun AddExerciseScreen(
    state: AddExerciseState,
    onAction: (AddExerciseVarietyAction) -> Unit
) {
    var isGalleryPermissionGranted by remember { mutableStateOf(value = false) }

    val permissionManager = rememberGalleryPermissionManager { granted ->
        isGalleryPermissionGranted = granted
    }

    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current

    val imagePicker = rememberImagePicker { bytes ->
        if (bytes != null) {
            onAction(AddExerciseVarietyAction.OnImageValueChange(imageBitmap = bytes.decodeToImageBitmap()))
        }
    }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.primary,
        unfocusedTextColor = MaterialTheme.colorScheme.primary,
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
        focusedPlaceholderColor = MaterialTheme.colorScheme.primary
    )

    LaunchedEffect(key1 = Unit) {
        isGalleryPermissionGranted = permissionManager.isPermissionGranted()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(resource = Res.string.add_exercise),
                            fontSize = 30.sp,
                            color = Color.White
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.surface
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isGalleryPermissionGranted) {
                if (state.image == null) {
                    Button(
                        onClick = { imagePicker.launch() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Panorama,
                            contentDescription = stringResource(resource = Res.string.upload_image)
                        )

                        Spacer(modifier = Modifier.width(width = 10.dp))

                        Text(
                            text = stringResource(resource = Res.string.upload_image)
                        )
                    }
                } else {
                    Image(
                        bitmap = state.image,
                        contentDescription = stringResource(resource = Res.string.exercise_image),
                        modifier = Modifier
                            .height(250.dp)
                            .clip(shape = RoundedCornerShape(size = 16.dp))
                    )
                }
            } else {
                Button(
                    onClick = { permissionManager.requestPermission() }
                ) {
                    Text(
                        text = stringResource(resource = Res.string.request_permission_for_photo_library)
                    )
                }
            }

            Spacer(modifier = Modifier.height(height = 20.dp))

            OutlinedTextField(
                value = state.name,
                onValueChange = { onAction(AddExerciseVarietyAction.OnNameValueChange(name = it)) },
                singleLine = true,
                label = { Text(text = stringResource(resource = Res.string.name)) },
                modifier = Modifier
                    .padding(all = 10.dp)
                    .focusRequester(focusRequester = focusRequester),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
                colors = textFieldColors,
                shape = RoundedCornerShape(size = 16.dp)
            )

            if (state.isExerciseNameExisting) {
                Text(
                    text = stringResource(resource = Res.string.exercise_with_that_name_already_exists),
                    color = MaterialTheme.colorScheme.error
                )
            }

//            Spacer(modifier = Modifier.height(height = 10.dp))

            OutlinedTextField(
                value = state.description,
                onValueChange = { onAction(AddExerciseVarietyAction.OnDescriptionValueChange(description = it)) },
                singleLine = false,
                label = { Text(text = stringResource(resource = Res.string.description)) },
                modifier = Modifier
                    .padding(all = 10.dp)
                    .focusRequester(focusRequester = focusRequester),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
                colors = textFieldColors,
                shape = RoundedCornerShape(size = 16.dp)
            )

            Spacer(modifier = Modifier.height(height = 20.dp))

            Column(
                modifier = Modifier.fillMaxWidth(fraction = 0.5f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                TextButton(
                    onClick = { onAction(AddExerciseVarietyAction.OnTargetMuscleGroupDropDownToggle) },
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(size = 16.dp)
                    )
                ) {
                    Text(
                        text = state.targetMuscleGroup.name,
                        fontSize = 22.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                DropdownMenu(
                    expanded = state.isDropDownMenuExpanded,
                    onDismissRequest = { onAction(AddExerciseVarietyAction.OnTargetMuscleGroupDropDownToggle) }
                ) {
                    MuscleGroup.entries.forEach { muscleGroup ->
                        DropdownMenuItem(
                            text = { Text(text = muscleGroup.name) },
                            onClick = { onAction(AddExerciseVarietyAction.OnTargetMuscleGroupValueChange(targetMuscleGroup = muscleGroup)) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(height = 20.dp))

            TextButton(
               onClick = { onAction(AddExerciseVarietyAction.OnSaveExercise) },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.surface,
                    disabledContainerColor = MaterialTheme.colorScheme.surface,
                    disabledContentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text (
                    text = stringResource(resource = Res.string.add_exercise)
                )
            }
        }
    }
}