package com.example.fittrack.training.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.empty_workout
import fittrack.shared.generated.resources.start
import org.jetbrains.compose.resources.stringResource

@Composable
fun EmptyWorkoutBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(all = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(resource = Res.string.empty_workout),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )

        TextButton(
            onClick = {},
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.inverseOnSurface,
                shape = RoundedCornerShape(size = 5.dp)
            )
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.width(width = 5.dp))

            Text(
                text = stringResource(resource = Res.string.start),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp
            )
        }
    }
}

@Preview
@Composable
fun EmptyWorkoutBarPreview() {
    MaterialTheme {
        EmptyWorkoutBar()
    }
}