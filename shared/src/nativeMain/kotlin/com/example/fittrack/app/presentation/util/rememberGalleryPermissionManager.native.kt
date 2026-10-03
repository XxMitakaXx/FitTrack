package com.example.fittrack.app.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.example.fittrack.app.domain.gallery_permission.GalleryPermissionManager

@Composable
actual fun rememberGalleryPermissionManager(
    onResult: (Boolean) -> Unit
): GalleryPermissionManager {
    return remember {
        IosGalleryPermissionManager(onResult = onResult)
    }
}