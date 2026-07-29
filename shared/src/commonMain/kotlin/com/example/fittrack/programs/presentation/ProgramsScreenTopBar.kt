package com.example.fittrack.programs.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import fittrack.shared.generated.resources.explore
import fittrack.shared.generated.resources.library
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProgramsScreenTopBar(
    onGoToExploreScreen: () -> Unit,
    onGoToLibraryScreen: () -> Unit,
    isExploreScreenVisible: Boolean
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
                    onClick = onGoToExploreScreen,
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    Text(
                        text = stringResource(resource = Res.string.explore),
                        fontSize = 32.sp,
                        fontWeight = if (isExploreScreenVisible) FontWeight.Bold else FontWeight.Medium
                    )
                }

                TextButton(
                    onClick = onGoToLibraryScreen,
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    Text(
                        text = stringResource(resource = Res.string.library),
                        fontSize = 32.sp,
                        fontWeight = if (!isExploreScreenVisible) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }
    )
}