package com.example.fittrack.body_weight.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fittrack.body_weight.presentation.components.BodyWeightOverallInfoPanel
import com.example.fittrack.body_weight.presentation.components.BodyWeightTopAppBar
import com.example.fittrack.body_weight.presentation.components.ProgressBodyWeightsHistory
import com.example.fittrack.training.presentation.components.AddWeightPopupContent
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.add
import fittrack.shared.generated.resources.add_dementions_so_we_can_start_tracking
import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BodyWeightRoot(
    viewModel: BodyWeightViewModel = koinViewModel(),
    onNavigateToTrainingScreen: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BodyWeightScreen(
        state = state,
        onAction = { action ->
            viewModel.onAction(action = action)
        },
        onNavigateToTrainingScreen = onNavigateToTrainingScreen
    )
}

@Composable
fun BodyWeightScreen(
    state: BodyWeightState,
    onAction: (BodyWeightAction) -> Unit,
    onNavigateToTrainingScreen: () -> Unit
) {
    Scaffold(
        topBar = {
            BodyWeightTopAppBar(
                onNavigateToTrainingScreen = onNavigateToTrainingScreen,
                onMakePopupVisible = { onAction(BodyWeightAction.OnMakePopupVisible) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues),
            horizontalAlignment = Alignment.Start,
        ) {
            val formatter = LocalDate.Format {
                year()
                char(' ')
                monthName(names = MonthNames.ENGLISH_FULL)
            }

            Spacer(modifier = Modifier.height(height = 20.dp))

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                val sortedByLocalDateProgressBodyWeights = state.progressBodyWeights.values.flatten().sortedBy { progressBodyWeight -> progressBodyWeight.recordedAt }
                BodyWeightOverallInfoPanel(
                    averageBodyWeight = state.averageBodyWeight,
                    averageBodyWeightStartDate = state.averageBodyWeightStartDate.format(format = formatter),
                    averageBodyWeightEndDate = state.averageBodyWeightEndDate.format(format = formatter),
                    changeBodyWeight = state.changeBodyWeight,
                    progressBodyWeights = sortedByLocalDateProgressBodyWeights,
                    currentBodyWeight = state.currentBodyWeight,
                    highestBodyWeight = state.highestBodyWeight,
                    lowestBodyWeight = state.lowesBodyWeight,
                    selectedTimeFilter = state.selectedTimeFilter,
                    onProgressBodyWeightTimeChange = { onAction(BodyWeightAction.OnProgressBodyWeightTimeChange(progressBodyWeightTime = it)) }
                )
            }

            if (state.progressBodyWeights.isNotEmpty()) {
                ProgressBodyWeightsHistory(
                    progressBodyWeights = state.progressBodyWeights,
                    unfoldedMonthWeightProgressesKeys = state.unfoldedMonthWeightProgressesKeys,
                    onMonthListClick = { onAction(BodyWeightAction.OnMonthListClick(dateString = it)) },
                    onProgressBodyWeightItemDelete = { onAction(BodyWeightAction.OnProgressBodyWeightItemDelete(dateString = it)) },
                )
            } else {
                Spacer(modifier = Modifier.height(height = 16.dp))
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(size = 16.dp)
                        )
                        .padding(all = 32.dp)
                ) {
                    TextButton(
                        onClick = { onAction(BodyWeightAction.OnMakePopupVisible) },
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(resource = Res.string.add_dementions_so_we_can_start_tracking),
                                fontSize = 25.sp
                            )

                            Spacer(modifier = Modifier.height(height = 10.dp))

                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(resource = Res.string.add)
                            )
                        }
                    }
                }
            }

            if (state.isPopupVisible) {
                val verticalOffsetDp = (500).dp
                val verticalOffsetPx = with(receiver = LocalDensity.current) { verticalOffsetDp.roundToPx() }
                val transitionState = remember {
                    MutableTransitionState(initialState = false).apply { targetState = true }
                }

                if (transitionState.isIdle && !transitionState.currentState) {
                    onAction(BodyWeightAction.OnMakePopupNotVisible)
                }

                Popup(
                    alignment = Alignment.Center,
                    onDismissRequest = { transitionState.targetState = false },
                    properties = PopupProperties(
                        dismissOnClickOutside = true,
                        focusable = true
                    ),
                    offset = IntOffset(x = 0, y = verticalOffsetPx)
                ) {
                    AnimatedVisibility(
                        visibleState = transitionState,
                        enter = fadeIn(animationSpec = tween(durationMillis = 300)) +
                                slideInVertically(
                                    animationSpec = tween(durationMillis = 300),
                                    initialOffsetY = { fullHeight -> fullHeight / 4 }
                                ),
                        exit = fadeOut(animationSpec = tween(durationMillis = 300)) +
                                slideOutVertically(
                                    animationSpec = tween(durationMillis = 300),
                                    targetOffsetY = { fullHeight -> fullHeight / 4 }
                                ),
                        modifier = Modifier.padding(all = 16.dp)
                    ) {
                        AddWeightPopupContent(
                            state = state,
                            onAction = onAction
                        )
                    }
                }
            }
        }

    }
}

@Preview
@Composable
fun BodyWeightScreenPreview() {
    MaterialTheme {
        BodyWeightScreen(
            state = BodyWeightState(),
            onAction = {},
            onNavigateToTrainingScreen = {}
        )
    }
}