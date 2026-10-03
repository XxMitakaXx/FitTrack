package com.example.fittrack.add_exercise.presentation.util

import androidx.compose.runtime.Composable

expect class ImagePicker {
    fun launch()
}

@Composable
expect fun rememberImagePicker(onImagePicked: (ByteArray?) -> Unit): ImagePicker