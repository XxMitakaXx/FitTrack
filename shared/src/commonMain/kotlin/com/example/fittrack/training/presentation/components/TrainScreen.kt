package com.example.fittrack.training.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.fittrack.programs.domain.models.enums.DaysPerWeek
import com.example.fittrack.programs.domain.models.enums.Equipment
import com.example.fittrack.programs.domain.models.enums.TrainingType
import com.example.fittrack.training.domain.models.ActiveProgram
import com.example.fittrack.training.domain.models.Exercise
import com.example.fittrack.training.domain.models.Training
import com.example.fittrack.training.domain.models.TrainingDay
import com.example.fittrack.training.domain.models.TrainingSet
import com.example.fittrack.training.domain.models.TrainingWeek
import com.example.fittrack.training.domain.models.UserProgress
import com.example.fittrack.training.domain.models.UserStats
import com.example.fittrack.training.domain.models.UserTrainingData
import com.example.fittrack.training.domain.models.enums.Gender
import com.example.fittrack.training.domain.models.enums.TrainingLevel
import com.example.fittrack.training.presentation.TrainingAction
import com.example.fittrack.training.presentation.TrainingState
import io.ktor.util.date.WeekDay
import kotlin.uuid.Uuid

@Composable
fun TrainScreen(
    state: TrainingState,
    onAction: (TrainingAction) -> Unit
) {
    Spacer(modifier = Modifier.height(height = 5.dp))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
            .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ActiveProgramContainer(
            state = state,
            onNavigateToProgramsScreens = { onAction(TrainingAction.OnNavigateToProgramsScreen) }
        )

        Spacer(modifier = Modifier.height(height =  10.dp))

        EmptyWorkoutBar()

        Spacer(modifier = Modifier.height(height = 40.dp))

        if (state.userTrainingData?.userStats != null) {
            UserProgressContainer(
                userStats = state.userTrainingData.userStats,
                onAddBodyWeightButtonClick = { onAction(TrainingAction.OnMakePopupVisible) },
                onNavigateToProgressBodyWeightScreen = { onAction(TrainingAction.OnNavigateToProgressBodyWightScreen) }
            )
        } else {
            Column {
                AddUserTrainingDataContainer(
                    onGoToAddUserTrainingDataScreen = { onAction(TrainingAction.OnNavigateToAddUserTrainingDataScreen) }
                )
            }
        }

        if (state.isPopupVisible) {
            val verticalOffsetDp = (-50).dp
            val verticalOffsetPx = with(receiver = LocalDensity.current) { verticalOffsetDp.roundToPx() }
            val transitionState = remember {
                MutableTransitionState(initialState = false).apply { targetState = true }
            }

            if (transitionState.isIdle && !transitionState.currentState) {
                onAction(TrainingAction.OnMakePopupNotVisible)
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

@Preview
@Composable
fun TrainingScreenPreview() {
    MaterialTheme {
        TrainScreen(
            state = TrainingState(
                isTrainScreenVisible = true,
                hasRecordedPrograms = true,
                isLoadingData = false,
                userTrainingData = UserTrainingData(
                    activeProgram = ActiveProgram(
                        id = Uuid.random(),
                        name = "Calistenics Power Up!",
                        trainingWeeks = listOf(
                            TrainingWeek(
                                trainingWeekId = Uuid.random(),
                                number = 1,
                                trainingDays = listOf(
                                    TrainingDay(
                                        trainingDayId = Uuid.random(),
                                        weekDay = WeekDay.THURSDAY,
                                        trainings = listOf(
                                            Training(
                                                trainingId = Uuid.random(),
                                                exercises = listOf(
                                                    Exercise(
                                                        exerciseId = Uuid.random(),
                                                        name = "Squad",
                                                        pictureUrl = "Squad",
                                                        trainingSets = listOf(
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 1
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 2
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 3
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 4
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 5
                                                            )
                                                        ),
                                                        targetReps = 8,
                                                        targetKg = 40.00
                                                    ),
                                                    Exercise(
                                                        exerciseId = Uuid.random(),
                                                        name = "Hack Squad",
                                                        pictureUrl = "Hack Squad",
                                                        trainingSets = listOf(
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 1
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 2
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 3
                                                            )
                                                        ),
                                                        targetReps = 8,
                                                        targetKg = 40.00
                                                    ),
                                                    Exercise(
                                                        exerciseId = Uuid.random(),
                                                        name = "Romanian Deadlift",
                                                        pictureUrl = "Romanian Deadlift",
                                                        trainingSets = listOf(
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 1
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 2
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 3
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 4
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 5
                                                            )
                                                        ),
                                                        targetReps = 8,
                                                        targetKg = 40.00
                                                    )
                                                )
                                            )
                                        )
                                    ),
                                    TrainingDay(
                                        trainingDayId = Uuid.random(),
                                        weekDay = WeekDay.THURSDAY,
                                        trainings = listOf(
                                            Training(
                                                trainingId = Uuid.random(),
                                                exercises = listOf(
                                                    Exercise(
                                                        exerciseId = Uuid.random(),
                                                        name = "Bench Press",
                                                        pictureUrl = "Bench Press",
                                                        trainingSets = listOf(
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 1
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 2
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 3
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 4
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 5
                                                            )
                                                        ),
                                                        targetReps = 8,
                                                        targetKg = 40.00
                                                    ),
                                                    Exercise(
                                                        exerciseId = Uuid.random(),
                                                        name = "Shoulder Press",
                                                        pictureUrl = "Shoulder Press",
                                                        trainingSets = listOf(
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 1
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 2
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 3
                                                            )
                                                        ),
                                                        targetReps = 8,
                                                        targetKg = 40.00
                                                    )
                                                )
                                            )
                                        )
                                    )
                                )
                            ),
                            TrainingWeek(
                                trainingWeekId = Uuid.random(),
                                number = 2,
                                trainingDays = listOf(
                                    TrainingDay(
                                        trainingDayId = Uuid.random(),
                                        weekDay = WeekDay.THURSDAY,
                                        trainings = listOf(
                                            Training(
                                                trainingId = Uuid.random(),
                                                exercises = listOf(
                                                    Exercise(
                                                        exerciseId = Uuid.random(),
                                                        name = "Squad",
                                                        pictureUrl = "Squad",
                                                        trainingSets = listOf(
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 1
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 2
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 3
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 4
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 5
                                                            )
                                                        ),
                                                        targetReps = 8,
                                                        targetKg = 40.00
                                                    ),
                                                    Exercise(
                                                        exerciseId = Uuid.random(),
                                                        name = "Hack Squad",
                                                        pictureUrl = "Hack Squad",
                                                        trainingSets = listOf(
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 1
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 2
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 3
                                                            )
                                                        ),
                                                        targetReps = 8,
                                                        targetKg = 40.00
                                                    ),
                                                    Exercise(
                                                        exerciseId = Uuid.random(),
                                                        name = "Romanian Deadlift",
                                                        pictureUrl = "Romanian Deadlift",
                                                        trainingSets = listOf(
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 1
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 2
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 3
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 4
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 5
                                                            )
                                                        ),
                                                        targetReps = 8,
                                                        targetKg = 40.00
                                                    )
                                                )
                                            )
                                        )
                                    ),
                                    TrainingDay(
                                        trainingDayId = Uuid.random(),
                                        weekDay = WeekDay.THURSDAY,
                                        trainings = listOf(
                                            Training(
                                                trainingId = Uuid.random(),
                                                exercises = listOf(
                                                    Exercise(
                                                        exerciseId = Uuid.random(),
                                                        name = "Bench Press",
                                                        pictureUrl = "Bench Press",
                                                        trainingSets = listOf(
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 1
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 2
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 3
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 4
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 5
                                                            )
                                                        ),
                                                        targetReps = 8,
                                                        targetKg = 40.00
                                                    ),
                                                    Exercise(
                                                        exerciseId = Uuid.random(),
                                                        name = "Shoulder Press",
                                                        pictureUrl = "Shoulder Press",
                                                        trainingSets = listOf(
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 1
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 2
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 3
                                                            )
                                                        ),
                                                        targetReps = 8,
                                                        targetKg = 40.00
                                                    )
                                                )
                                            )
                                        )
                                    )
                                )
                            ),
                            TrainingWeek(
                                trainingWeekId = Uuid.random(),
                                number = 3,
                                trainingDays = listOf(
                                    TrainingDay(
                                        trainingDayId = Uuid.random(),
                                        weekDay = WeekDay.THURSDAY,
                                        trainings = listOf(
                                            Training(
                                                trainingId = Uuid.random(),
                                                exercises = listOf(
                                                    Exercise(
                                                        exerciseId = Uuid.random(),
                                                        name = "Squad",
                                                        pictureUrl = "Squad",
                                                        trainingSets = listOf(
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 1
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 2
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 3
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 4
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 5
                                                            )
                                                        ),
                                                        targetReps = 8,
                                                        targetKg = 40.00
                                                    ),
                                                    Exercise(
                                                        exerciseId = Uuid.random(),
                                                        name = "Hack Squad",
                                                        pictureUrl = "Hack Squad",
                                                        trainingSets = listOf(
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 1
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 2
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 3
                                                            )
                                                        ),
                                                        targetReps = 8,
                                                        targetKg = 40.00
                                                    ),
                                                    Exercise(
                                                        exerciseId = Uuid.random(),
                                                        name = "Romanian Deadlift",
                                                        pictureUrl = "Romanian Deadlift",
                                                        trainingSets = listOf(
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 1
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 2
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 3
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 4
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 5
                                                            )
                                                        ),
                                                        targetReps = 8,
                                                        targetKg = 40.00
                                                    )
                                                )
                                            )
                                        )
                                    ),
                                    TrainingDay(
                                        trainingDayId = Uuid.random(),
                                        weekDay = WeekDay.THURSDAY,
                                        trainings = listOf(
                                            Training(
                                                trainingId = Uuid.random(),
                                                exercises = listOf(
                                                    Exercise(
                                                        exerciseId = Uuid.random(),
                                                        name = "Bench Press",
                                                        pictureUrl = "Bench Press",
                                                        trainingSets = listOf(
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 1
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 2
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 3
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 4
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 5
                                                            )
                                                        ),
                                                        targetReps = 8,
                                                        targetKg = 40.00
                                                    ),
                                                    Exercise(
                                                        exerciseId = Uuid.random(),
                                                        name = "Shoulder Press",
                                                        pictureUrl = "Shoulder Press",
                                                        trainingSets = listOf(
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 1
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 2
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 3
                                                            )
                                                        ),
                                                        targetReps = 8,
                                                        targetKg = 40.00
                                                    )
                                                )
                                            )
                                        )
                                    )
                                )
                            ),
                            TrainingWeek(
                                trainingWeekId = Uuid.random(),
                                number = 4,
                                trainingDays = listOf(
                                    TrainingDay(
                                        trainingDayId = Uuid.random(),
                                        weekDay = WeekDay.THURSDAY,
                                        trainings = listOf(
                                            Training(
                                                trainingId = Uuid.random(),
                                                exercises = listOf(
                                                    Exercise(
                                                        exerciseId = Uuid.random(),
                                                        name = "Squad",
                                                        pictureUrl = "Squad",
                                                        trainingSets = listOf(
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 1
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 2
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 3
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 4
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 5
                                                            )
                                                        ),
                                                        targetReps = 8,
                                                        targetKg = 40.00
                                                    ),
                                                    Exercise(
                                                        exerciseId = Uuid.random(),
                                                        name = "Hack Squad",
                                                        pictureUrl = "Hack Squad",
                                                        trainingSets = listOf(
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 1
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 2
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 3
                                                            )
                                                        ),
                                                        targetReps = 8,
                                                        targetKg = 40.00
                                                    ),
                                                    Exercise(
                                                        exerciseId = Uuid.random(),
                                                        name = "Romanian Deadlift",
                                                        pictureUrl = "Romanian Deadlift",
                                                        trainingSets = listOf(
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 1
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 2
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 3
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 4
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 5
                                                            )
                                                        ),
                                                        targetReps = 8,
                                                        targetKg = 40.00
                                                    )
                                                )
                                            )
                                        )
                                    ),
                                    TrainingDay(
                                        trainingDayId = Uuid.random(),
                                        weekDay = WeekDay.THURSDAY,
                                        trainings = listOf(
                                            Training(
                                                trainingId = Uuid.random(),
                                                exercises = listOf(
                                                    Exercise(
                                                        exerciseId = Uuid.random(),
                                                        name = "Bench Press",
                                                        pictureUrl = "Bench Press",
                                                        trainingSets = listOf(
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 1
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 2
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 3
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 4
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 40.00,
                                                                reps = 3,
                                                                number = 5
                                                            )
                                                        ),
                                                        targetReps = 8,
                                                        targetKg = 40.00
                                                    ),
                                                    Exercise(
                                                        exerciseId = Uuid.random(),
                                                        name = "Shoulder Press",
                                                        pictureUrl = "Shoulder Press",
                                                        trainingSets = listOf(
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 1
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 2
                                                            ),
                                                            TrainingSet(
                                                                id = Uuid.random(),
                                                                kilograms = 30.00,
                                                                reps = 8,
                                                                number = 3
                                                            )
                                                        ),
                                                        targetReps = 8,
                                                        targetKg = 40.00
                                                    )
                                                )
                                            )
                                        )
                                    )
                                )
                            )
                        ),
                        trainingLevel = TrainingLevel.ADVANCED,
                        daysPerWeek = DaysPerWeek.THREE,
                        recommendedDays = listOf(WeekDay.MONDAY, WeekDay.WEDNESDAY, WeekDay.FRIDAY),
                        totalCountUsed = 546,
                        userProgress = UserProgress(
                            week = 4,
                            day = 4
                        ),
                        trainingType = TrainingType.OLYMPIC_WEIGHTLIFTING,
                        timePerWorkoutMinutes = 40,
                        rate = 4.5,
                        equipment = Equipment.FULL_GYM
                    ),
                    userStats = UserStats(
                        id = Uuid.random(),
                        weightKg = 65,
                        heightCm = 180,
                        gender = Gender.MALE,
                        age = 21,
                        lifetimeWorkouts = 15,
                        lifetimeLiftedKg = 40000.00,
                        lifetimeTrainingHours = 18,
                        lifetimePRs = 104,
                        progressPhotos = emptyList(),
                        progressBodyWeights = emptyList()
                    )
            ),
                isPopupVisible = true
            ),
            onAction = {}
        )
    }
}

