package com.example.fittrack.core.presentation

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asSkiaBitmap
import org.jetbrains.skia.EncodedImageFormat
import org.jetbrains.skia.Image

actual fun ImageBitmap.toByteArray(quality: Int): ByteArray {
    val skiaBitmap = this.asSkiaBitmap()
    val skiaImage = Image.makeFromBitmap(bitmap = skiaBitmap)

    val encodedData = skiaImage.encodeToData(format = EncodedImageFormat.JPEG)
        ?: throw Exception("Failed to encode image")

    return encodedData.bytes
}