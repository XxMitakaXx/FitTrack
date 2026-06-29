package com.example.fittrack

import androidx.compose.ui.window.ComposeUIViewController
import com.example.fittrack.app.App
import com.example.fittrack.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}