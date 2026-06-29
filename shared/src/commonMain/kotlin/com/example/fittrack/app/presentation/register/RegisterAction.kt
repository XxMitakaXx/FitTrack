package com.example.fittrack.app.presentation.register

sealed interface RegisterAction {
    data class OnEmailValueChange(val email: String): RegisterAction
    data class OnPasswordValueChange(val password: String): RegisterAction
    data class OnFirstNameValueChange(val firstName: String): RegisterAction
    data class OnLastNameValueChange(val lastName: String): RegisterAction
    data class OnConfirmPasswordValueChange(val confirmPassword: String): RegisterAction
    data object OnTogglePasswordVisibility: RegisterAction
    data object OnToggleConfirmPasswordVisibility: RegisterAction
    data object OnRegister: RegisterAction
    data object OnGoToLogin: RegisterAction
}