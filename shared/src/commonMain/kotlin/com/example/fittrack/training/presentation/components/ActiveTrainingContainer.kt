package com.example.fittrack.training.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Dataset
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fittrack.training.presentation.TrainingState
import com.example.fittrack.training.presentation.util.toOrdinalNumber
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.add
import fittrack.shared.generated.resources.create_program
import fittrack.shared.generated.resources.dataset
import fittrack.shared.generated.resources.sets
import fittrack.shared.generated.resources.start_week_week_number_day_number
import fittrack.shared.generated.resources.target_reps
import fittrack.shared.generated.resources.week_number_day_number
import fittrack.shared.generated.resources.workout_plan
import org.jetbrains.compose.resources.stringResource

@Composable
fun ActiveProgramContainer(
    state: TrainingState,
    onNavigateToProgramsScreens: () -> Unit
) {
    if (state.userTrainingData?.activeProgram != null) {
        val weekProgress = state.userTrainingData.activeProgram.userProgress.week
        val dayProgress = state.userTrainingData.activeProgram.userProgress.day
        val trainingForToday = state.userTrainingData.activeProgram.trainingWeeks.find { week -> week.number == weekProgress }?.trainingDays?.find { trainingDay -> trainingDay.weekDay.toOrdinalNumber() == dayProgress }?.trainings?.first()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
                .padding(all = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.95f)
                    .height(height = 90.dp)
                    .background(color = MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = state.userTrainingData.activeProgram.name,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(all = 8.dp)
                )

                Row(
                    modifier = Modifier
                        .height(height = 25.dp)
                        .width(width = 140.dp)
                        .background(color = MaterialTheme.colorScheme.primary),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Dataset,
                        contentDescription = stringResource(resource = Res.string.dataset),
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )

                    Text(
                        text = stringResource(resource = Res.string.workout_plan),
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${state.userProgressInPercentage} %"
                )

                Spacer(modifier = Modifier.width(width = 10.dp))

                Box(
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(size = 16.dp))
                        .background(color = MaterialTheme.colorScheme.primary)
                        .fillMaxWidth()
                        .height(height = 8.dp),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Box(
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.onPrimaryContainer)
                            .fillMaxHeight()
                            .fillMaxWidth(fraction = state.userProgressInPercentage)
                    )
                }
            }

            Spacer(modifier = Modifier.height(height = 15.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(resource = Res.string.week_number_day_number, weekProgress, dayProgress),
                        modifier = Modifier.width(width = 160.dp)
                    )

                    Text(
                        text = stringResource(resource = Res.string.sets),
                        modifier = Modifier.width(width = 60.dp)
                    )

                    Text(
                        text = stringResource(resource = Res.string.target_reps),
                        modifier = Modifier.width(width = 100.dp)
                    )
                }

                LazyColumn(
                    modifier = Modifier.height(height = 85.dp)
                ) {
                    items(
                        count = trainingForToday!!.exercises.size,
                        key = { id ->  trainingForToday.exercises[id].exerciseId },
                        contentType = { id -> id }
                    ) {
                        val exercise = trainingForToday.exercises[it]

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 3.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = exercise.name,
                                modifier = Modifier.width(width = 170.dp)
                            )

                            Text(
                                text = exercise.trainingSets.size.toString(),
                                modifier = Modifier.width(width = 90.dp)
                            )

                            Text(
                                text = exercise.targetReps.toString(),
                                modifier = Modifier.width(width = 60.dp)
                            )
                        }
                    }
                }

            }

            Spacer(modifier = Modifier.height(height = 20.dp))

            TextButton(
                onClick = {},
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        shape = RoundedCornerShape(size = 5.dp)
                    )
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = ""
                )

                Text(
                    text = stringResource(resource = Res.string.start_week_week_number_day_number, weekProgress, dayProgress),
                    color = MaterialTheme.colorScheme.background
                )
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 300.dp)
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(all = 16.dp)
                .clickable(onClick = { onNavigateToProgramsScreens() }),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(resource = Res.string.create_program),
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(height = 10.dp))

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(resource = Res.string.add),
                modifier = Modifier.size(size = 40.dp)
            )
        }
    }
}