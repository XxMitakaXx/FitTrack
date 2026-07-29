package com.example.fittrack.training.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fittrack.training.domain.models.UserStats
import com.example.fittrack.training.domain.models.enums.Gender
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.add
import fittrack.shared.generated.resources.analytics
import fittrack.shared.generated.resources.body_weight
import fittrack.shared.generated.resources.hours
import fittrack.shared.generated.resources.lifetime_stats
import fittrack.shared.generated.resources.lifted
import fittrack.shared.generated.resources.my_progress
import fittrack.shared.generated.resources.prs
import fittrack.shared.generated.resources.workouts
import org.jetbrains.compose.resources.stringResource
import kotlin.math.roundToInt
import kotlin.uuid.Uuid

@Composable
fun UserProgressContainer(
    userStats: UserStats?,
    onAddBodyWeightButtonClick: () -> Unit,
    onNavigateToProgressBodyWeightScreen: () -> Unit
) {
    if (userStats != null) {
        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(resource = Res.string.my_progress),
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(height = 10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(size = 16.dp)
                        )
                        .width(width = 200.dp)
                        .height(height = 200.dp)
                        .clickable(onClick = { onNavigateToProgressBodyWeightScreen() })
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(resource = Res.string.body_weight),
                            fontSize = 18.sp,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        IconButton(
                            onClick = { onAddBodyWeightButtonClick() }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = stringResource(resource = Res.string.add)
                            )
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Analytics,
                            contentDescription = stringResource(resource = Res.string.analytics),
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Spacer(modifier = Modifier.width(width = 10.dp))

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .width(width = 200.dp)
                        .height(height = 200.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(size = 16.dp)
                        )
                ) {
                    Text(
                        text = stringResource(resource = Res.string.lifetime_stats),
                        fontSize = 18.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            LifetimeStatsTextComponent(
                                lifetimeStat = userStats.lifetimeWorkouts.toString(),
                                lifetimeStatName = stringResource(resource = Res.string.workouts)
                            )

                            LifetimeStatsTextComponent(
                                lifetimeStat = userStats.lifetimeTrainingHours.toString(),
                                lifetimeStatName = stringResource(resource = Res.string.hours)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            LifetimeStatsTextComponent(
                                lifetimeStat = "${(userStats.lifetimeLiftedKg / 1000).roundToInt()}K",
                                lifetimeStatName = stringResource(resource = Res.string.lifted)
                            )

                            LifetimeStatsTextComponent(
                                lifetimeStat = userStats.lifetimePRs.toString(),
                                lifetimeStatName = stringResource(resource = Res.string.prs)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(height = 10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.background(color = MaterialTheme.colorScheme.background).width(width = 180.dp).height(height = 180.dp)
                ) {
                    Text(
                        text = stringResource(resource = Res.string.lifetime_stats),
                        fontSize = 14.sp
                    )

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            LifetimeStatsTextComponent(
                                lifetimeStat = userStats.lifetimeWorkouts.toString(),
                                lifetimeStatName = stringResource(resource = Res.string.workouts)
                            )

                            LifetimeStatsTextComponent(
                                lifetimeStat = userStats.lifetimeTrainingHours.toString(),
                                lifetimeStatName = stringResource(resource = Res.string.hours)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            LifetimeStatsTextComponent(
                                lifetimeStat = "${(userStats.lifetimeLiftedKg / 1000).roundToInt()}K",
                                lifetimeStatName = stringResource(resource = Res.string.lifted)
                            )

                            LifetimeStatsTextComponent(
                                lifetimeStat = userStats.lifetimePRs.toString(),
                                lifetimeStatName = stringResource(resource = Res.string.prs)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(width = 10.dp))

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.background(color = MaterialTheme.colorScheme.background).width(width = 180.dp).height(height = 180.dp)
                ) {
                    Text(
                        text = stringResource(resource = Res.string.lifetime_stats)
                    )

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            LifetimeStatsTextComponent(
                                lifetimeStat = userStats.lifetimeWorkouts.toString(),
                                lifetimeStatName = stringResource(resource = Res.string.workouts)
                            )

                            LifetimeStatsTextComponent(
                                lifetimeStat = userStats.lifetimeTrainingHours.toString(),
                                lifetimeStatName = stringResource(resource = Res.string.hours)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            LifetimeStatsTextComponent(
                                lifetimeStat = "${(userStats.lifetimeLiftedKg / 1000).roundToInt()}K",
                                lifetimeStatName = stringResource(resource = Res.string.lifted)
                            )

                            LifetimeStatsTextComponent(
                                lifetimeStat = userStats.lifetimePRs.toString(),
                                lifetimeStatName = stringResource(resource = Res.string.prs)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun UserProgressContainerPreview() {
    MaterialTheme {
        UserProgressContainer(
            userStats = UserStats(
                id = Uuid.random(),
                weightKg = 60,
                heightCm = 180,
                gender = Gender.MALE,
                age = 21,
                lifetimeWorkouts = 18,
                lifetimeLiftedKg = 50000.00,
                lifetimeTrainingHours = 20,
                lifetimePRs = 104,
                progressPhotos = emptyList(),
                progressBodyWeights = emptyList()
            ),
            onAddBodyWeightButtonClick = {},
            onNavigateToProgressBodyWeightScreen = {}
        )
    }
}