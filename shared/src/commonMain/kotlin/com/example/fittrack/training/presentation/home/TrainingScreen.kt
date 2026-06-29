package com.example.fittrack.training.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fittrack.app.presentation.login.LoginViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TrainingRoot(
    viewModel: TrainingViewModel = koinViewModel(),
    loginViewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    TrainingScreen(
        state = state,
        isLoggedIn = loginViewModel.state.value.isLoggedIn
    )
}

@Composable
fun TrainingScreen(
    state: TrainingState,
    isLoggedIn: Boolean
) {

}