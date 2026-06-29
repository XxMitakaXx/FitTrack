package com.example.fittrack.app.presentation.register

sealed interface RegisterEvent {
    data object OnRegister: RegisterEvent
}
