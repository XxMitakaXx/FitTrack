package com.example.fittrack.body_weight.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.add
import fittrack.shared.generated.resources.arrow_back
import fittrack.shared.generated.resources.body_weight
import org.jetbrains.compose.resources.stringResource

@Composable
fun BodyWeightTopAppBar(
    onNavigateToTrainingScreen: () -> Unit,
    onMakePopupVisible: () -> Unit
) {
    TopAppBar(
        title = { Text(
            text = stringResource(resource = Res.string.body_weight),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        ) },
        navigationIcon = {
            IconButton(
                onClick = { onNavigateToTrainingScreen() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(resource = Res.string.arrow_back),
                )
            }
        },
        actions = {
            IconButton(
                onClick = { onMakePopupVisible() }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(resource = Res.string.add),
                )
            }
        }
    )
}