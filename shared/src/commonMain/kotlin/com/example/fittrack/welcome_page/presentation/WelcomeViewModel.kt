package com.example.fittrack.welcome_page.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class WelcomeViewModel: ViewModel() {

    private val _events = Channel<WelcomeEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: WelcomeAction) {
        when(action) {
            is WelcomeAction.OnGoToLogin -> {
                navigateToLogin()
            }
            is WelcomeAction.OnGoToRegister -> {
                navigateToRegister()
            }
        }
    }

    private fun navigateToRegister() {
        viewModelScope.launch {
            _events.send(element = WelcomeEvent.OnGoToRegister)
        }
    }

    private fun navigateToLogin() {
        viewModelScope.launch {
            _events.send(element = WelcomeEvent.OnGoToLogin)
        }
    }
}