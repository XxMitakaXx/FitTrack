package com.example.fittrack.app.presentation.util

import androidx.compose.runtime.Composable
import com.example.fittrack.app.domain.gallery_permission.GalleryPermissionManager

@Composable
expect fun rememberGalleryPermissionManager(
    onResult: (Boolean) -> Unit
): GalleryPermissionManager