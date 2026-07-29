package com.example.fittrack.training.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.history
import fittrack.shared.generated.resources.settings
import fittrack.shared.generated.resources.train
import org.jetbrains.compose.resources.stringResource

@Composable
fun TrainingScreenTopBar(
    onGoToTrainScreen: () -> Unit,
    onGoToHistoryScreen: () -> Unit,
    isTrainScreenVisible: Boolean
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(all = 8.dp)
            ) {
                TextButton(
                    onClick = onGoToTrainScreen,
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    Text(
                        text = stringResource(resource = Res.string.train),
                        fontSize = 32.sp,
                        fontWeight = if (isTrainScreenVisible) FontWeight.Bold else FontWeight.Medium
                    )
                }

                TextButton(
                    onClick = onGoToHistoryScreen,
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    Text(
                        text = stringResource(resource = Res.string.history),
                        fontSize = 32.sp,
                        fontWeight = if (!isTrainScreenVisible) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        },
       actions = {
           IconButton(
               onClick = {},
               Modifier
                   .padding(all = 8.dp)
           ) {
               Icon(
                   imageVector = Icons.Default.Settings,
                   contentDescription = stringResource(resource = Res.string.settings),
                   modifier = Modifier.size(size = 35.dp)
               )
           }
       }
    )
}