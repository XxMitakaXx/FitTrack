package com.example.fittrack.terms_and_privacy_policy.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SlidingSegmentedControl(
    options: List<String>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val trackColor = MaterialTheme.colorScheme.primaryContainer
    val indicatorColor = MaterialTheme.colorScheme.primary
    val selectedTextColor = Color.White
    val unselectedTextColor = MaterialTheme.colorScheme.onPrimaryContainer

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(size = 24.dp))
            .background(color = trackColor)
            .padding(all = 8.dp)
    ) {
        val tabWidth = maxWidth / options.size
        val indicatorOffset by animateDpAsState(
            targetValue = tabWidth * selectedIndex,
            animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing),
            label = "indicatorOffset"
        )

        Box(
            modifier = Modifier
                .offset(x = indicatorOffset)
                .width(width = tabWidth)
                .height(height = 50.dp)
                .clip(shape = CircleShape)
                .background(color = indicatorColor)
        )

        Row {
            options.forEachIndexed { index, label ->
                val textColor by animateColorAsState(
                    targetValue = if (index == selectedIndex) selectedTextColor else unselectedTextColor,
                    animationSpec = tween(durationMillis = 250),
                    label = "tabTextColor"
                )

                Box(
                    modifier = Modifier
                        .weight(weight = 1f)
                        .height(height = 50.dp)
                        .clip(shape = CircleShape)
                        .clickable { onSelected(index) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = textColor
                    )
                }
            }
        }
    }
}