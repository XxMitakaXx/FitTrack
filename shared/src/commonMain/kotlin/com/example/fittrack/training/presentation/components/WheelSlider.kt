package com.example.fittrack.training.presentation.components

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.fittrack.training.presentation.util.centerItemIndex

@Composable
fun WheelSlider(
    items: List<String>,
    state: LazyListState,
    modifier: Modifier = Modifier
) {
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = state)

    val currentIndex by remember { derivedStateOf { state.centerItemIndex() } }

    LazyColumn(
        state = state,
        flingBehavior = snapBehavior,
        modifier = modifier.height(height = 150.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 50.dp)
    ) {
        items(count = items.size) { index ->
            val isSelected = currentIndex == index
            Text(
                text = items[index],
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier
                    .height(height = 50.dp)
                    .wrapContentHeight(align = Alignment.CenterVertically)
                    .alpha(alpha = if (isSelected) 1f else 0.3f),
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray
            )
        }
    }
}