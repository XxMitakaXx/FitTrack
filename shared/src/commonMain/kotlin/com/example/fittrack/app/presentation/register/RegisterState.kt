package com.example.fittrack.app.presentation.register

import androidx.compose.runtime.Immutable

@Immutable
data class RegisterState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val emailExists: Boolean = false,
    val isEmailValid: Boolean = true,
    val isPasswordAndConfirmPasswordAreEqual: Boolean = true,
    val isFirstNameBlank: Boolean = false,
    val isLastNameBlank: Boolean = false,
    val isEmailBlank: Boolean = false,
    val isPasswordBlank: Boolean = false
)
