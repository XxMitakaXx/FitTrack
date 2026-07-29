package com.example.fittrack.training.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fittrack.training.domain.models.enums.WeightDimension
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.kg
import fittrack.shared.generated.resources.lbs
import org.jetbrains.compose.resources.stringResource

@Composable
fun WeightDimensionSelector(
    selectedDimension: WeightDimension,
    onSelectedWeightDimension: (WeightDimension) -> Unit
) {
    Row(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.onBackground,
            shape = RoundedCornerShape(size = 16.dp)
        )
            .padding(all = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        TextButton(
            onClick = { onSelectedWeightDimension(WeightDimension.LBS) },
            modifier = Modifier.background(
                color = if (selectedDimension == WeightDimension.LBS) Color.Gray else MaterialTheme.colorScheme.primary ,
                shape = RoundedCornerShape(size = 16.dp)
            )
        ) {
            Text(
                text = stringResource(resource = Res.string.lbs),
                color = if (selectedDimension == WeightDimension.LBS) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                fontWeight = if (selectedDimension == WeightDimension.LBS) FontWeight.Bold else FontWeight.Normal,
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.width(width = 10.dp))

        TextButton(
            onClick = { onSelectedWeightDimension(WeightDimension.KG) },
            modifier = Modifier.background(
                color = if (selectedDimension == WeightDimension.KG) Color.Gray else MaterialTheme.colorScheme.primary ,
                shape = RoundedCornerShape(size = 16.dp)
            )
        ) {
            Text(
                text = stringResource(resource = Res.string.kg),
                color = if (selectedDimension == WeightDimension.KG) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                fontWeight = if (selectedDimension == WeightDimension.KG) FontWeight.Bold else FontWeight.Normal,
                fontSize = 15.sp
            )
        }

    }
}