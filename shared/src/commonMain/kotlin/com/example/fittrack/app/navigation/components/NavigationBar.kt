package com.example.fittrack.app.navigation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.BreakfastDining
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material.icons.filled.Train
import androidx.compose.material.icons.filled.VideoStable
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.fittrack.app.navigation.NavigationRoute
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.analytics
import fittrack.shared.generated.resources.programs
import fittrack.shared.generated.resources.train
import org.jetbrains.compose.resources.stringResource

@Composable
fun NavigationBar(
    currentPage: NavigationRoute,
    onGoToTrain: () -> Unit,
    onGoToPrograms: () -> Unit,
    onGoToAnalytics: () -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = currentPage == NavigationRoute.ProgramsScreen,
            onClick = { onGoToPrograms() },
            icon = {
                Icon(
                    imageVector = Icons.Default.VideoStable,
                    contentDescription = stringResource(resource = Res.string.programs)
                )
            },
            label = {
                Text(
                    text = stringResource(resource = Res.string.programs)
                )
            }
        )

        NavigationBarItem(
            selected = currentPage == NavigationRoute.TrainingScreen,
            onClick = { onGoToTrain() },
            icon = {
                Icon(
                    imageVector = Icons.Default.BreakfastDining,
                    contentDescription = stringResource(resource = Res.string.train)
                )
            },
            label = {
                Text(
                    text = stringResource(resource = Res.string.train)
                )
            }
        )

        NavigationBarItem(
            selected = currentPage == NavigationRoute.AnalyticsScreen,
            onClick = { onGoToAnalytics() },
            icon = {
                Icon(
                    imageVector = Icons.Default.Analytics,
                    contentDescription = stringResource(resource = Res.string.analytics)
                )
            },
            label = {
                Text(
                    text = stringResource(resource = Res.string.analytics)
                )
            }
        )
    }
}

@Preview
@Composable
fun NavigationBarPreview() {
    MaterialTheme {
        NavigationBar(
            currentPage = NavigationRoute.ProgramsScreen,
            onGoToTrain = {},
            onGoToPrograms = {},
            onGoToAnalytics = {}
        )
    }
}