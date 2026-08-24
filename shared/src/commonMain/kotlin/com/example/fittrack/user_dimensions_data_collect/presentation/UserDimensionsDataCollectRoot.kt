package com.example.fittrack.user_dimensions_data_collect.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fittrack.core.presentation.ObserveAsEvent
import com.example.fittrack.training.domain.models.enums.Gender
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.add_dementions_so_we_can_start_tracking
import fittrack.shared.generated.resources.age
import fittrack.shared.generated.resources.arrow_back
import fittrack.shared.generated.resources.female
import fittrack.shared.generated.resources.height
import fittrack.shared.generated.resources.male
import fittrack.shared.generated.resources.not_specified
import fittrack.shared.generated.resources.save
import fittrack.shared.generated.resources.weight
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserDimensionsDataCollectRoot(
    viewModel: UserDimensionsDataCollectViewModel = koinViewModel(),
    onNavigateToTrainingScreen: () -> Unit
) {

    ObserveAsEvent(events = viewModel.events) { event ->
        when(event) {
            is UserDimensionsDataCollectEvent.OnSavedUserDimensionsDataSuccess -> onNavigateToTrainingScreen()
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    UserDimensionsDataCollectScreen(
        state = state,
        onAction = { action ->
            viewModel.onAction(action = action)
        },
        onNavigateToTrainingScreen = onNavigateToTrainingScreen
    )
}

@Composable
fun UserDimensionsDataCollectScreen(
    state: UserDimensionsDataCollectState,
    onAction: (UserDimensionsDataCollectAction) -> Unit,
    onNavigateToTrainingScreen: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { onNavigateToTrainingScreen() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowLeft,
                            contentDescription = stringResource(resource = Res.string.arrow_back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        val focusRequester = remember {
            FocusRequester()
        }
        val focusManager = LocalFocusManager.current

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
        ) {
            Text(
                text = stringResource(resource = Res.string.add_dementions_so_we_can_start_tracking),
                fontSize = 25.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(height = 60.dp))

            OutlinedTextField(
                value = state.height,
                onValueChange = { onAction(UserDimensionsDataCollectAction.OnHeightValueChange(height = it)) },
                singleLine = true,
                label = { Text(text = stringResource(resource = Res.string.height)) },
                modifier = Modifier
                    .padding(all = 10.dp)
                    .focusRequester(focusRequester = focusRequester),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Next) }),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
                    unfocusedTextColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(size = 16.dp),
            )

            OutlinedTextField(
                value = state.age,
                onValueChange = { onAction(UserDimensionsDataCollectAction.OnAgeValueChange(age = it)) },
                singleLine = true,
                label = { Text(text = stringResource(resource = Res.string.age)) },
                modifier = Modifier
                    .padding(all = 10.dp)
                    .focusRequester(focusRequester = focusRequester),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Next) }),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
                    unfocusedTextColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(size = 16.dp),
            )

            Spacer(modifier = Modifier.height(height = 10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth(fraction = 0.7f)
            ) {
                TextButton(
                    onClick = { onAction(UserDimensionsDataCollectAction.OnGenderValueChange(gender = Gender.MALE.toString())) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (state.gender == Gender.MALE.toString()) MaterialTheme.colorScheme.onBackground else Color.Transparent,
                        contentColor = if (state.gender == Gender.MALE.toString()) Color.Black else MaterialTheme.colorScheme.primary,
                        disabledContainerColor = Color.Unspecified,
                        disabledContentColor = Color.Unspecified
                    )
                ) {
                    Text(
                        text = stringResource(resource = Res.string.male)
                    )
                }

                TextButton(
                    onClick = { onAction(UserDimensionsDataCollectAction.OnGenderValueChange(gender = Gender.FEMALE.toString())) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (state.gender == Gender.FEMALE.toString()) MaterialTheme.colorScheme.onBackground else Color.Transparent,
                        contentColor = if (state.gender == Gender.FEMALE.toString()) Color.Black else MaterialTheme.colorScheme.primary,
                        disabledContainerColor = Color.Unspecified,
                        disabledContentColor = Color.Unspecified
                    )
                ) {
                    Text(
                        text = stringResource(resource = Res.string.female)
                    )
                }

                TextButton(
                    onClick = { onAction(UserDimensionsDataCollectAction.OnGenderValueChange(gender = Gender.NON_SPECIFIED.toString())) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (state.gender == Gender.NON_SPECIFIED.toString()) MaterialTheme.colorScheme.onBackground else Color.Transparent,
                        contentColor = if (state.gender == Gender.NON_SPECIFIED.toString()) Color.Black else MaterialTheme.colorScheme.primary,
                        disabledContainerColor = Color.Unspecified,
                        disabledContentColor = Color.Unspecified
                    )
                ) {
                    Text(
                        text = stringResource(resource = Res.string.not_specified)
                    )
                }
            }

            Spacer(modifier = Modifier.height(height = 10.dp))

            TextButton(
                onClick = { onAction(UserDimensionsDataCollectAction.OnSave) },
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = Color.Unspecified,
                    disabledContentColor = Color.Unspecified
                ),
                modifier = Modifier.width(width = 130.dp)
            ) {
                Text(
                    text = stringResource(resource = Res.string.save)
                )
            }
        }
    }
}

@Preview
@Composable
fun UserDimensionsDataCollectScreenPreview() {
    MaterialTheme {
        UserDimensionsDataCollectScreen(
            state = UserDimensionsDataCollectState(
                weight = "65",
                height = "182",
                age = "21",
                gender = Gender.MALE.toString()
            ),
            onAction = {},
            onNavigateToTrainingScreen = {}
        )
    }
}