package com.example.fittrack.training.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.add
import fittrack.shared.generated.resources.add_dementions_so_we_can_start_tracking
import org.jetbrains.compose.resources.stringResource

@Composable
fun AddUserTrainingDataContainer(
    onGoToAddUserTrainingDataScreen: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .height(height = 250.dp)
            .background(color = MaterialTheme.colorScheme.primary)
            .clickable(onClick = { onGoToAddUserTrainingDataScreen() })
    ) {
        Text(
            text = stringResource(resource = Res.string.add_dementions_so_we_can_start_tracking),
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