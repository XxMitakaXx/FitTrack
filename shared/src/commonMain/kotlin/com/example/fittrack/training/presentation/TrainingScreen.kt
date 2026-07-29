package com.example.fittrack.training.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fittrack.core.presentation.ObserveAsEvent
import com.example.fittrack.training.domain.models.ProgressBodyWeight
import com.example.fittrack.training.presentation.components.TrainScreen
import com.example.fittrack.training.presentation.components.TrainingHistoryScreen
import com.example.fittrack.training.presentation.components.TrainingScreenTopBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TrainingRoot(
    viewModel: TrainingViewModel = koinViewModel(),
    onNavigateToProgramsScreen: () -> Unit,
    onNavigateToAddUserTrainingDataScreen: () -> Unit,
    onNavigateToBodyWeightProgressScreen: (progressBodyWeights: List<ProgressBodyWeight>) -> Unit,
    bottomNavigationBar: @Composable () -> Unit
) {

    ObserveAsEvent(events = viewModel.events) { event ->
        when(event) {
            is TrainingEvent.OnNavigateToProgramsScreen -> { onNavigateToProgramsScreen() }
            is TrainingEvent.OnNavigateToAddUserTrainingDataScreen -> { onNavigateToAddUserTrainingDataScreen() }
            is TrainingEvent.OnUserTrainingDataSaved -> {}
            is TrainingEvent.OnNavigateToProgressBodyWeightScreen -> { onNavigateToBodyWeightProgressScreen(event.progressBodyWeights) }
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    TrainingScreen(
        state = state,
        onAction = { action ->
            viewModel.onAction(action = action)
        },
        bottomNavigationBar = bottomNavigationBar
    )
}

@Composable
fun TrainingScreen(
    state: TrainingState,
    onAction: (TrainingAction) -> Unit,
    bottomNavigationBar: @Composable () -> Unit
) {

    Scaffold(
        topBar = {
            TrainingScreenTopBar(
                onGoToTrainScreen = {
                    onAction(TrainingAction.OnTrainButtonClick)
                },
                onGoToHistoryScreen = {
                    onAction(TrainingAction.OnHistoryButtonClick)
                },
                isTrainScreenVisible = state.isTrainScreenVisible,
            )
        },
        bottomBar = bottomNavigationBar
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            AnimatedContent(
                targetState = state.isTrainScreenVisible,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(weight = 1f),
                transitionSpec = {
                    val duration = 400
                    val customEasing = FastOutSlowInEasing

                    if (state.isTrainScreenVisible) {
                        slideInHorizontally(
                            animationSpec = tween(durationMillis = duration, easing = customEasing),
                            initialOffsetX = { width -> -width }
                        ) togetherWith slideOutHorizontally(
                            animationSpec = tween(durationMillis = duration, easing = customEasing),
                            targetOffsetX = { width -> width }
                        )
                    } else {
                        slideInHorizontally(
                            animationSpec = tween(durationMillis = duration, easing = customEasing),
                            initialOffsetX = { width -> width }
                        ) togetherWith slideOutHorizontally(
                            animationSpec = tween(durationMillis = duration, easing = customEasing),
                            targetOffsetX = { width -> -width }
                        )
                    }
                }
            ) { isTrainScreenVisible ->
                if (isTrainScreenVisible) {
                    TrainScreen(
                        state = state,
                        onAction = onAction
                    )
                } else {
                    TrainingHistoryScreen(
                        state = state
                    )
                }
            }
        }
    }
}