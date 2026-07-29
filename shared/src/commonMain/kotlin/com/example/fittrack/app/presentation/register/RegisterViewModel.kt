package com.example.fittrack.app.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fittrack.app.domain.authentication.AuthenticationDataSource
import com.example.fittrack.app.domain.authentication.RegisterRequest
import com.example.fittrack.core.data.util.onError
import com.example.fittrack.core.data.util.onSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authenticationDataSource: AuthenticationDataSource
): ViewModel() {
    private val _state = MutableStateFlow(value = RegisterState())
    val state = _state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 1000L),
            initialValue = RegisterState()
        )

    private val _events = Channel<RegisterEvent>()
    val events = _events.receiveAsFlow()


    fun onAction(action: RegisterAction) {
            when(action) {
                is RegisterAction.OnFirstNameValueChange -> editFirstName(firstName = action.firstName)
                is RegisterAction.OnLastNameValueChange -> editLastName(lastName = action.lastName)
                is RegisterAction.OnEmailValueChange -> {
                    editEmail(email = action.email)
                    checkEmail(email = action.email)
                }
                is RegisterAction.OnPasswordValueChange -> editPassword(password = action.password)
                is RegisterAction.OnConfirmPasswordValueChange -> editConfirmPassword(confirmPassword = action.confirmPassword)
                is RegisterAction.OnTogglePasswordVisibility -> togglePasswordVisibility()
                is RegisterAction.OnToggleConfirmPasswordVisibility -> toggleConfirmPasswordVisibility()
                is RegisterAction.OnRegister -> register()
                else -> Unit
            }
    }

    private fun checkPasswordAndConfirmPasswordAreEqual() {
        if (_state.value.password != _state.value.confirmPassword) {
            viewModelScope.launch {
                _state.update { it.copy(
                    isPasswordAndConfirmPasswordAreEqual = false
                ) }
            }
        } else {
            viewModelScope.launch {
                _state.update { it.copy(
                    isPasswordAndConfirmPasswordAreEqual = true
                ) }
            }
        }
    }

    private fun register() {
        checkPasswordAndConfirmPasswordAreEqual()
        checkFirstNameIsBlank()
        checkLastNameIsBlank()
        checkEmailIsBlank()
        checkPasswordIsBlank()
        checkEmail(_state.value.email)

        val isEmailValid = _state.value.isEmailValid
        val isPasswordAndConfirmPasswordAreEqual = _state.value.isPasswordAndConfirmPasswordAreEqual
        val isFirstNameBlank = _state.value.isFirstNameBlank
        val isLastNameBlank = _state.value.isLastNameBlank
        val isEmailBlank = _state.value.isEmailBlank
        val isPasswordBlank = _state.value.isPasswordBlank


        if (
            !_state.value.isPasswordAndConfirmPasswordAreEqual ||
            _state.value.isFirstNameBlank ||
            _state.value.isLastNameBlank ||
            _state.value.isEmailBlank ||
            _state.value.isPasswordBlank ||
            !_state.value.isEmailValid
            ) return

        viewModelScope.launch {
            authenticationDataSource.register(
                registerRequest = RegisterRequest(
                    firstName = _state.value.firstName,
                    lastName = _state.value.lastName,
                    email = _state.value.email,
                    password = _state.value.password
                )
            )
                .onSuccess {
                    viewModelScope.launch {
                        _state.update { it.copy(
                             emailExists = false
                        ) }

                        _events.send(element = RegisterEvent.OnRegister)
                    }
                }
                .onError {
                    viewModelScope.launch {
                        _state.update { it.copy(
                            emailExists = true
                        ) }
                    }
                }
        }
    }

    private fun toggleConfirmPasswordVisibility() {
        viewModelScope.launch {
            _state.update { it.copy(
                isConfirmPasswordVisible = !_state.value.isConfirmPasswordVisible
            ) }
        }
    }

    private fun togglePasswordVisibility() {
        viewModelScope.launch {
            _state.update { it.copy(
                isPasswordVisible = !_state.value.isPasswordVisible
            ) }
        }
    }

    private fun editConfirmPassword(confirmPassword: String) {
        viewModelScope.launch {
            _state.update { it.copy(
                confirmPassword = confirmPassword
            ) }
        }
    }

    private fun checkPasswordIsBlank() {
        if (_state.value.password.isBlank()) {
            viewModelScope.launch {
                _state.update { it.copy(
                    isPasswordBlank = true
                ) }
            }
        } else {
            viewModelScope.launch {
                _state.update { it.copy(
                    isPasswordBlank = false
                ) }
            }
        }
    }

    private fun editPassword(password: String) {
        viewModelScope.launch {
            _state.update { it.copy(
                password = password
            ) }
        }
    }

    private fun checkEmail(email: String) {
        val regex = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"

        if (email.matches(regex.toRegex())) {
            viewModelScope.launch {
                _state.update { it.copy(
                    isEmailValid = true
                ) }
            }
        } else {
            viewModelScope.launch {
                _state.update { it.copy(
                    isEmailValid = false
                ) }
            }
        }
    }

    private fun checkEmailIsBlank() {
        if (_state.value.email.isBlank()) {
            viewModelScope.launch {
                _state.update { it.copy(
                    isEmailBlank = true
                ) }
            }
        } else {
            viewModelScope.launch {
                _state.update { it.copy(
                    isEmailBlank = false
                ) }
            }
        }
    }

    private fun editEmail(email: String) {
        viewModelScope.launch {
            _state.update { it.copy(
                email = email
            ) }
        }
    }

    private fun checkLastNameIsBlank() {
        if (_state.value.lastName.isBlank()) {
            viewModelScope.launch {
                _state.update { it.copy(
                    isLastNameBlank = true
                ) }
            }
        } else {
            viewModelScope.launch {
                _state.update { it.copy(
                    isLastNameBlank = false
                ) }
            }
        }
    }

    private fun editLastName(lastName: String) {
        viewModelScope.launch {
            _state.update { it.copy(
                lastName = lastName
            ) }
        }
    }

    private fun checkFirstNameIsBlank() {
        if (_state.value.firstName.isBlank()) {
            viewModelScope.launch {
                _state.update { it.copy(
                    isFirstNameBlank= true
                ) }
            }
        } else {
            viewModelScope.launch {
                _state.update { it.copy(
                    isFirstNameBlank = false
                ) }
            }
        }
    }

    private fun editFirstName(firstName: String) {
        viewModelScope.launch {
            _state.update { it.copy(
                firstName = firstName
            ) }
        }
    }
}