package com.example.fittrack.core.presentation

import androidx.compose.ui.graphics.ImageBitmap

expect fun ImageBitmap.toByteArray(quality: Int): ByteArray