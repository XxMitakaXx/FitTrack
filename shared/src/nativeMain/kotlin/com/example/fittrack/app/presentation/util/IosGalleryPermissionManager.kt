package com.example.fittrack.app.presentation.util

import com.example.fittrack.app.domain.gallery_permission.GalleryPermissionManager
import platform.Photos.PHAuthorizationStatusAuthorized
import platform.Photos.PHAuthorizationStatusNotDetermined
import platform.Photos.PHPhotoLibrary
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

class IosGalleryPermissionManager(
    private val onResult: (Boolean) -> Unit
): GalleryPermissionManager {
    override fun requestPermission() {
        val status = PHPhotoLibrary.authorizationStatus()
        when(status) {
            PHAuthorizationStatusAuthorized -> onResult(true)
            PHAuthorizationStatusNotDetermined -> {
                PHPhotoLibrary.requestAuthorization { newStatus ->
                    dispatch_async(queue = dispatch_get_main_queue()) {
                        onResult(newStatus == PHAuthorizationStatusAuthorized)
                    }
                }
            }
            else -> onResult(false)
        }
    }

    override fun isPermissionGranted(): Boolean {
        return PHPhotoLibrary.authorizationStatus() == PHAuthorizationStatusAuthorized
    }
}