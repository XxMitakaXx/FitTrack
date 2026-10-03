package com.example.fittrack.app.presentation.util

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.fittrack.app.domain.gallery_permission.GalleryPermissionManager

@Composable
actual fun rememberGalleryPermissionManager(
    onResult: (Boolean) -> Unit
): GalleryPermissionManager {
    val context = LocalContext.current

    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted -> onResult(isGranted) }
    )

    return remember(key1 = launcher, key2 = context) {
        AndroidGalleryPermissionManager(
            requestAction = { launcher.launch(input = permission) },
            checkAction = {
                ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
            }
        )
    }
}