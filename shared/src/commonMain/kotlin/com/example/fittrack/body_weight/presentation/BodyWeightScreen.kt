package com.example.fittrack.body_weight.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fittrack.body_weight.presentation.components.BodyWeightTopAppBar
import com.example.fittrack.body_weight.presentation.components.ProgressWeightsHistory
import com.example.fittrack.training.presentation.TrainingAction
import com.example.fittrack.training.presentation.components.AddWeightPopupContent
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.history
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(resource = Res.string.history),
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,

            )

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 3.dp)
                    .background(color = MaterialTheme.colorScheme.surface)
            )

            ProgressWeightsHistory(
                progressBodyWeights = state.progressBodyWeights,
                averageWeight = state.averageWeight,
                onMonthListClick = { onAction(BodyWeightAction.OnMonthListClick(number = it)) }
            )

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