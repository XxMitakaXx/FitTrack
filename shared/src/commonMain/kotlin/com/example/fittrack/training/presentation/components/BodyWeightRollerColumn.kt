package com.example.fittrack.training.presentation.components

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun <T> BodyWeightRollerColumn(
    state: LazyListState,
    items: List<T>
) {
    LazyColumn(
        state = state,
        flingBehavior = rememberSnapFlingBehavior(lazyListState = state),
        modifier = Modifier.height(height = 120.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { Box(modifier = Modifier.height(height = 40.dp)) }

        items(
            count = items.size,
            key = { index -> items[index].hashCode() }
        ) { index ->
            val isSelected = index == state.firstVisibleItemIndex

            Box(
                modifier = Modifier.height(height = 40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = items[index].toString(),
                    fontSize = if (isSelected) 20.sp else 16.sp,
                    modifier = Modifier.alpha(alpha = if (isSelected) 1f else 0.4f)
                )
            }
        }

        item { Box(modifier = Modifier.height(height = 40.dp)) }
    }
}