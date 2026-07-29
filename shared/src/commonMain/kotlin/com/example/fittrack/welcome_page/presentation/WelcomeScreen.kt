package com.example.fittrack.welcome_page.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fittrack.core.presentation.ObserveAsEvent
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.gear
import fittrack.shared.generated.resources.login
import fittrack.shared.generated.resources.register
import fittrack.shared.generated.resources.welcome_page_image
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WelcomeRoot(
    viewModel: WelcomeViewModel = koinViewModel(),
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    ObserveAsEvent(events = viewModel.events) { event ->
        when (event) {
            is WelcomeEvent.OnGoToLogin -> onNavigateToLogin()
            is WelcomeEvent.OnGoToRegister -> onNavigateToRegister()
        }
    }

    WelcomeScreen(
        onAction = { action -> viewModel.onAction(action = action) }
    )
}

@Composable
fun WelcomeScreen(
    onAction: (WelcomeAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(resource = Res.drawable.gear),
            contentDescription = stringResource(resource = Res.string.welcome_page_image),
            modifier = Modifier.size(size = 350.dp)
        )

        Spacer(modifier = Modifier.height(height = 80.dp))

        TextButton(
            onClick = { onAction(WelcomeAction.OnGoToLogin) },
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(size = 6.dp)
                )
                .padding(horizontal = 42.dp)
        ) {
            Text(
                text = stringResource(resource = Res.string.login),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(height = 30.dp))

        TextButton(
            onClick = { onAction(WelcomeAction.OnGoToRegister) },
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(size = 6.dp)
                )
                .padding(horizontal = 30.dp)
        ) {
            Text(
                text = stringResource(resource = Res.string.register),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 20.sp
            )
        }
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    MaterialTheme {
        WelcomeScreen(
            onAction = {}
        )
    }
}