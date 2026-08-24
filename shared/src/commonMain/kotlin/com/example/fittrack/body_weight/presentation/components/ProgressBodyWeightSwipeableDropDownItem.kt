package com.example.fittrack.body_weight.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.fittrack.training.domain.models.ProgressBodyWeight
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.delete
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import kotlin.math.roundToInt

@Composable
fun ProgressBodyWeightSwipeableDropDownItem(
    isRevealed: Boolean,
    actions: @Composable RowScope.() -> Unit,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    var contentMenuWidth by remember {
        mutableFloatStateOf(value = 0f)
    }
    val offset = remember {
        Animatable(initialValue = 0f)
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = isRevealed,  key2 = contentMenuWidth) {
        if (isRevealed) {
            offset.animateTo(targetValue = -contentMenuWidth)
        } else {
            offset.animateTo(targetValue = 0f)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Min)
    ) {
        Row(
            modifier = Modifier
                .align(alignment = Alignment.CenterEnd)
                .onSizeChanged {
                    contentMenuWidth = it.width.toFloat()
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            actions()
        }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(x = offset.value.roundToInt(), y = 0) }
                .pointerInput(key1 = contentMenuWidth) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = {_, dragAmount ->
                            scope.launch {
                                val newOffset = (offset.value + dragAmount)
                                    .coerceIn(minimumValue = -contentMenuWidth, maximumValue = 0f)
                                offset.snapTo(targetValue = newOffset)
                            }
                        },
                        onDragEnd = {
                            when {
                                offset.value <= -contentMenuWidth / 2f -> {
                                    scope.launch {
                                        offset.animateTo(targetValue = -contentMenuWidth)
                                    }
                                }
                                else -> {
                                    scope.launch {
                                        offset.animateTo(targetValue = 0f)
                                    }
                                }
                            }
                        }
                    )
                },
            color = MaterialTheme.colorScheme.errorContainer
        ) {
            content()
        }
    }
}