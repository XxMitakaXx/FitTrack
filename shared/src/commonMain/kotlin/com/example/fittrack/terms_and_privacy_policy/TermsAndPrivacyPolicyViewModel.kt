package com.example.fittrack.terms_and_privacy_policy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TermsAndPrivacyPolicyViewModel: ViewModel() {

    private val _events = Channel<TermsAndPrivacyPolicyEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: TermsAndPrivacyPolicyAction) {
        when (action) {
            is TermsAndPrivacyPolicyAction.OnGoBack -> onNavigateBack()
        }
    }

    private fun onNavigateBack() {
        viewModelScope.launch {
            _events.send(element = TermsAndPrivacyPolicyEvent.OnGoBack)
        }
    }
}