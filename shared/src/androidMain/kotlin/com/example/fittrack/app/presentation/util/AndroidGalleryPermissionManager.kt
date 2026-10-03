package com.example.fittrack.app.presentation.util

import com.example.fittrack.app.domain.gallery_permission.GalleryPermissionManager

class AndroidGalleryPermissionManager(
    private val requestAction: () -> Unit,
    private val checkAction: () -> Boolean
): GalleryPermissionManager {
    override fun requestPermission() = requestAction()
    override fun isPermissionGranted(): Boolean = checkAction()
}