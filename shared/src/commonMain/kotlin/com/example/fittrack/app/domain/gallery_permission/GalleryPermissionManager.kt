package com.example.fittrack.app.domain.gallery_permission

interface GalleryPermissionManager {
    fun requestPermission()
    fun isPermissionGranted(): Boolean
}