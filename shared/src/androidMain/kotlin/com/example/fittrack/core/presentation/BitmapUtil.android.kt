package com.example.fittrack.core.presentation

import android.graphics.Bitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import java.io.ByteArrayOutputStream

actual fun ImageBitmap.toByteArray(quality: Int): ByteArray {
    val androidBitmap = this.asAndroidBitmap()
    val stream = ByteArrayOutputStream()

    androidBitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream)
    return stream.toByteArray()
}