package com.example.fittrack.programs.presentation.components

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Rowing
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.fittrack.programs.presentation.ProgramsAction
import com.example.fittrack.programs.presentation.ProgramsState
import com.example.fittrack.programs.presentation.util.SelectedProgramPage
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.add
import fittrack.shared.generated.resources.all_private_programs
import fittrack.shared.generated.resources.create_exercise
import fittrack.shared.generated.resources.create_your_program
import fittrack.shared.generated.resources.private
import fittrack.shared.generated.resources.program
import fittrack.shared.generated.resources.public
import fittrack.shared.generated.resources.saved
import org.jetbrains.compose.resources.stringResource

@Composable
fun LibraryScreen(
    onAction: (ProgramsAction) -> Unit,
    state: ProgramsState,
    onNavigateToAddExerciseVariety: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.surface)
        ) {
            TextButton(
                onClick = {  },
                modifier = Modifier.padding(all = 8.dp)
            ) {
                Text(
                    text = stringResource(resource = Res.string.public),
                    fontSize = 18.sp
                )
            }

            TextButton(
                onClick = {  },
                modifier = Modifier.padding(all = 8.dp)
            ) {
                Text(
                    text = stringResource(resource = Res.string.private),
                    fontSize = 18.sp
                )
            }

            TextButton(
                onClick = {  },
                modifier = Modifier.padding(all = 8.dp)
            ) {
                Text(
                    text = stringResource(resource = Res.string.saved),
                    fontSize = 18.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(height = 20.dp))

        if (state.isAdmin) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.9f)
            ) {
                val dashPathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(20f, 20f), phase =0f)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 100.dp)
                        .clickable(onClick = { onNavigateToAddExerciseVariety() })
                        .drawBehind {
                            drawRoundRect(
                                color = Color.Gray,
                                style = Stroke(
                                    width = 4.dp.toPx(),
                                    pathEffect = dashPathEffect
                                )
                            )
                        }
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = stringResource(resource = Res.string.add)
                    )

                    Spacer(modifier = Modifier.width(width = 10.dp))

                    Text(
                        text = stringResource(resource = Res.string.create_exercise)
                    )
                }
            }

            Spacer(modifier = Modifier.height(height = 20.dp))
        }

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(fraction = 0.9f)
        ) {
            val dashPathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(20f, 20f), phase =0f)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 100.dp)
                    .clickable(onClick = {  })
                    .drawBehind {
                        drawRoundRect(
                            color = Color.Gray,
                            style = Stroke(
                                width = 4.dp.toPx(),
                                pathEffect = dashPathEffect
                            )
                        )
                    }
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = stringResource(resource = Res.string.add)
                )

                Spacer(modifier = Modifier.width(width = 10.dp))

                Text(
                    text = stringResource(resource = Res.string.create_your_program)
                )
            }
        }

        if (state.selectedProgramPage == SelectedProgramPage.PUBLIC) {
            val publicPrograms = state.createdPrograms.filter { program -> program.isPublic }

            if (publicPrograms.isNotEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Rowing,
                        contentDescription = ""
                    )

                    Text(
                        text = stringResource(resource = Res.string.all_private_programs)
                    )

                    publicPrograms.forEach { program ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            AsyncImage(
                                model = program.imageUrl,
                                contentDescription = stringResource(resource = Res.string.program)
                            )

                            Column(
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = program.name
                                )
                            }
                        }
                    }
                }
            }
        } else if (state.selectedProgramPage == SelectedProgramPage.PRIVATE) {
            val privatePrograms = state.createdPrograms.filter { program -> !program.isPublic }

            privatePrograms.forEach { program ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = program.imageUrl,
                        contentDescription = stringResource(resource = Res.string.program)
                    )

                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = program.name
                        )
                    }
                }
            }
        } else if (state.selectedProgramPage == SelectedProgramPage.SAVED) {
            val savedPrograms = state.savedPrograms

            savedPrograms.forEach { program ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = program.imageUrl,
                        contentDescription = stringResource(resource = Res.string.program)
                    )

                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = program.name
                        )
                    }
                }
            }
        }
    }
}