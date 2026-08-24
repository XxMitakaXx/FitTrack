package com.example.fittrack.body_weight.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fittrack.body_weight.domain.models.enums.ProgressBodyWeightTime
import com.example.fittrack.body_weight.presentation.util.roundToDecimal
import com.example.fittrack.training.domain.models.ProgressBodyWeight
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.average
import fittrack.shared.generated.resources.change
import fittrack.shared.generated.resources.current
import fittrack.shared.generated.resources.highest
import fittrack.shared.generated.resources.kg
import fittrack.shared.generated.resources.lowest
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import org.jetbrains.compose.resources.stringResource
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.roundToInt

@Composable
fun BodyWeightOverallInfoPanel(
    averageBodyWeight: Double,
    averageBodyWeightStartDate: String,
    averageBodyWeightEndDate: String,
    changeBodyWeight: Int,
    progressBodyWeights: List<ProgressBodyWeight>,
    currentBodyWeight: Int,
    highestBodyWeight: Int,
    lowestBodyWeight: Int,
    selectedTimeFilter: ProgressBodyWeightTime,
    onProgressBodyWeightTimeChange: (ProgressBodyWeightTime) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth(fraction = 0.95f)
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(size = 16.dp)
            )
            .padding(all = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(resource = Res.string.average),
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = averageBodyWeight.toString(),
                        fontSize = 35.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(width = 5.dp))

                    Text(
                        text = stringResource(resource = Res.string.kg).toLowerCase(locale = Locale.current),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray
                    )
                }

                Text(
                    text = "$averageBodyWeightStartDate - $averageBodyWeightEndDate",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(width = 120.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = stringResource(resource = Res.string.change),
                    fontSize = 12.sp,
                    color = Color.Gray
                )

                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = changeBodyWeight.toString(),
                        fontSize = 35.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(width = 5.dp))

                    Text(
                        text = stringResource(resource = Res.string.kg).toLowerCase(locale = Locale.current),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(height = 20.dp))

        if (progressBodyWeights.isNotEmpty()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val yMin = floor(x = lowestBodyWeight.toDouble()).toFloat()
                val yMax = ceil(x = highestBodyWeight.toDouble()).toFloat()
                val bodyWeightRange = (yMax - yMin).coerceAtLeast(minimumValue = 1f)

                val minTime = progressBodyWeights.minOf { it.recordedAt.toEpochDays() }
                val maxTime = progressBodyWeights.maxOf { it.recordedAt.toEpochDays() }
                val timeRange = (maxTime - minTime).coerceAtLeast(minimumValue = 1L)

                val textMeasurer = rememberTextMeasurer()
                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 280.dp)
                        .clipToBounds()
                        .padding(all = 16.dp)
                ) {
                    val leftPadding = 100f
                    val bottomPadding = 60f
                    val topPadding = 40f
                    val rightPadding = 40f

                    val chartWidth = size.width - leftPadding - rightPadding
                    val chartHeight = size.height - topPadding - bottomPadding

                    val getY: (Float) -> Float = { bodyWeight ->
                        val fraction = (bodyWeight - yMin) / bodyWeightRange
                        topPadding + (chartHeight * (1f - fraction))
                    }

                    val getX: (LocalDate) -> Float = { localDate ->
                        val epochDay = localDate.toEpochDays()
                        if (timeRange == 0L) {
                            leftPadding
                        } else {
                            val timeFraction = (epochDay - minTime).toFloat() / timeRange.toFloat()
                            leftPadding + (chartWidth * timeFraction)
                        }
                    }

                    val dashedPathEffect = PathEffect.dashPathEffect(intervals = floatArrayOf(10f, 10f), phase = 0f)
                    var currentLevel = yMin
                    val labelStyle = TextStyle(fontSize = 12.sp, color = Color.Gray)

                    while (currentLevel <= yMax + 10.0f) {
                        val yPos = getY(currentLevel)

                        drawLine(
                            color = Color.LightGray.copy(alpha = 0.4f),
                            start = Offset(x = leftPadding, y = yPos),
                            end = Offset(x = size.width - rightPadding, y = yPos),
                            strokeWidth = 1.dp.toPx(),
                            pathEffect = dashedPathEffect
                        )

                        val text = currentLevel.roundToDecimal(decimals = 2).toString()
                        val measureText = textMeasurer.measure(text = text, style = labelStyle)

                        drawText(
                            textMeasurer = textMeasurer,
                            text = text,
                            style = labelStyle,
                            topLeft = Offset(
                                x = 0f,
                                y = yPos - (measureText.size.height / 2f)
                            )
                        )

                        currentLevel += 10.0f
                    }

//                    progressBodyWeights.forEach { progressBodyWeight ->
//                        val text = progressBodyWeight.recordedAt.toString()
//                        val measuredText = textMeasurer.measure(text = text, style = labelStyle)
//                        val xPos = getX(progressBodyWeight.recordedAt)
//
//                        drawText(
//                            textMeasurer = textMeasurer,
//                            text = text,
//                            style = labelStyle,
//                            topLeft = Offset(
//                                x = xPos - (measuredText.size.width / 2f),
//                                y = size.height - measuredText.size.height
//                            )
//                        )
//                    }

                    val path = Path()
                    var previousX = getX(progressBodyWeights[0].recordedAt)
                    var previousY = getY(progressBodyWeights[0].weight.toFloat())

                    path.moveTo(x = previousX, y = previousY)

                    for (i in 1 until progressBodyWeights.size) {
                        val currentX = getX(progressBodyWeights[i].recordedAt)
                        val currentY = getY(progressBodyWeights[i].weight.toFloat())
                        val controlPointX = (previousX + currentX) / 2f

                        path.cubicTo(
                            x1 = controlPointX,
                            y1 = previousY,
                            x2 = controlPointX,
                            y2 = currentY,
                            x3 = currentX,
                            y3 = currentY
                        )

                        previousX = currentX
                        previousY = currentY
                    }

                    drawPath(
                        path = path,
                        color = Color.Green,
                        style = Stroke(
                            width = 3.dp.toPx(),
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )

                    progressBodyWeights.forEachIndexed { index, progressBodyWeight ->
                        val x = getX(progressBodyWeight.recordedAt)
                        val y = getY(progressBodyWeight.weight.toFloat())

                        drawCircle(
                            color = Color.Black,
                            radius = 6.dp.toPx(),
                            center = Offset(
                                x = x,
                                y = y
                            )
                        )

                        drawCircle(
                            color = Color.LightGray,
                            radius = 4.dp.toPx(),
                            center = Offset(
                                x = x,
                                y = y
                            )
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(height = 20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            ProgressBodyWeightTime.entries.forEach { progressBodyWeightTime ->
                TextButton(
                    onClick = { onProgressBodyWeightTimeChange(progressBodyWeightTime) },
                    modifier = Modifier
                        .background(
                            color = if (selectedTimeFilter == progressBodyWeightTime) Color.Green else Color.DarkGray,
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                        .height(height = 40.dp)
                        .width(width = 60.dp)
                ) {
                    Text(
                        text = progressBodyWeightTime.formatedText,
                        fontSize = 13.sp,
                        color = if (selectedTimeFilter == progressBodyWeightTime) Color.DarkGray else Color.Gray
                    )
                }

                Spacer(modifier = Modifier.width(width = 10.dp))
            }
        }

        Spacer(modifier = Modifier.height(height = 20.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = currentBodyWeight.toString(),
                        fontSize = 25.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(width = 5.dp))

                    Text(
                        text = stringResource(resource = Res.string.kg).toLowerCase(locale = Locale.current),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray
                    )
                }

                Text(
                    text = stringResource(resource = Res.string.current),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(width = 55.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = highestBodyWeight.toString(),
                        fontSize = 25.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(width = 5.dp))

                    Text(
                        text = stringResource(resource = Res.string.kg).toLowerCase(locale = Locale.current),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray
                    )
                }

                Text(
                    text = stringResource(resource = Res.string.highest),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.width(width = 55.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = lowestBodyWeight.toString(),
                        fontSize = 25.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width(width = 5.dp))

                    Text(
                        text = stringResource(resource = Res.string.kg).toLowerCase(locale = Locale.current),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Gray
                    )
                }

                Text(
                    text = stringResource(resource = Res.string.lowest),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}