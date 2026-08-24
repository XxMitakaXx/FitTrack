package com.example.fittrack.programs.presentation

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
import com.example.fittrack.programs.presentation.components.ExploreScreen
import com.example.fittrack.programs.presentation.components.LibraryScreen
import com.example.fittrack.programs.presentation.components.ProgramsScreenTopBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProgramsRoot(
    viewModel: ProgramsViewModel = koinViewModel(),
    bottomNavigationBar: @Composable () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ProgramsScreen(
        state = state,
        onAction = { action ->
            viewModel.onAction(action = action)
        },
        bottomNavigationBar = bottomNavigationBar
    )
}

@Composable
fun ProgramsScreen(
    state: ProgramsState,
    onAction: (ProgramsAction) -> Unit,
    bottomNavigationBar: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            ProgramsScreenTopBar(
                onGoToExploreScreen = {
                    onAction(ProgramsAction.OnExploreButtonClick)
                },
                onGoToLibraryScreen = {
                    onAction(ProgramsAction.OnLibraryButtonClick)
                },
                isExploreScreenVisible = state.isExploreScreenVisible
            )
        },
        bottomBar = bottomNavigationBar
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            AnimatedContent(
                targetState = state.isExploreScreenVisible,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(weight = 1f),
                transitionSpec = {
                    val duration = 400
                    val customEasing = FastOutSlowInEasing

                    if (state.isExploreScreenVisible) {
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
            ) { isExploreScreenVisible ->
                if (isExploreScreenVisible) {
                    ExploreScreen(
                        onAction = onAction,
                        state = state
                    )
                } else {
                    LibraryScreen(
                        onAction = onAction,
                        state =  state
                    )
                }
            }
        }
    }
}