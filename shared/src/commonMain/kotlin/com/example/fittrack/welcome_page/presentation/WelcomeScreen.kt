package com.example.fittrack.welcome_page.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fittrack.core.presentation.ObserveAsEvent
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.by_continuing_you_agree_to_our_terms_and_privacy_policy
import fittrack.shared.generated.resources.fitrack
import fittrack.shared.generated.resources.hero_rings
import fittrack.shared.generated.resources.hero_rings_dark
import fittrack.shared.generated.resources.login
import fittrack.shared.generated.resources.personal_plans_workout_tracking_and_progress_you_can_see
import fittrack.shared.generated.resources.register
import fittrack.shared.generated.resources.train_smarter_move_better
import fittrack.shared.generated.resources.welcome_page_image
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WelcomeRoot(
    viewModel: WelcomeViewModel = koinViewModel(),
    onNavigateToLogin: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateTermsAndPrivacyPolicyScreen: () -> Unit
) {
    ObserveAsEvent(events = viewModel.events) { event ->
        when (event) {
            is WelcomeEvent.OnGoToLogin -> onNavigateToLogin()
            is WelcomeEvent.OnGoToRegister -> onNavigateToRegister()
            is WelcomeEvent.OnGoToTermsAndPrivacyPolicy -> onNavigateTermsAndPrivacyPolicyScreen()
        }
    }

    WelcomeScreen(
        onAction = { action -> viewModel.onAction(action = action) }
    )
}

@Composable
fun WelcomeScreen(
    onAction: (WelcomeAction) -> Unit,
    isSystemDarkTheme: Boolean = isSystemInDarkTheme()
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = if (isSystemDarkTheme) painterResource(resource = Res.drawable.hero_rings_dark) else painterResource(resource = Res.drawable.hero_rings),
                contentDescription = stringResource(resource = Res.string.welcome_page_image),
                modifier = Modifier.size(size = 300.dp)
            )

            Spacer(modifier = Modifier.height(height = 20.dp))

            Column(
                modifier = Modifier.fillMaxWidth(fraction = 0.9f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = stringResource(resource = Res.string.hero_rings),
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(size = 24.dp)
                    )

                    Spacer(modifier = Modifier.width(width = 6.dp))

                    Text(
                        text = stringResource(resource = Res.string.fitrack),
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(height = 15.dp))

                Text(
                    text = stringResource(resource = Res.string.train_smarter_move_better),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 28.sp,
                    lineHeight = 30.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(height = 10.dp))

                Text(
                    text = stringResource(resource = Res.string.personal_plans_workout_tracking_and_progress_you_can_see),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 18.sp,
                    lineHeight = 21.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(height = 40.dp))

                TextButton(
                    onClick = { onAction(WelcomeAction.OnGoToLogin) },
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.95f)
                        .height(height = 60.dp)
                        .clip(shape = RoundedCornerShape(size = 32.dp))
                        .padding(horizontal = 30.dp),
                    colors = ButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        disabledContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Text(
                        text = stringResource(resource = Res.string.login),
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(height = 20.dp))

                OutlinedButton(
                    onClick = { onAction(WelcomeAction.OnGoToRegister) },
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.95f)
                        .height(height = 60.dp)
                        .clip(shape = RoundedCornerShape(size = 32.dp))
                        .padding(horizontal = 30.dp),
                    border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
                    colors = ButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        disabledContainerColor = Color.Transparent,
                        disabledContentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Text(
                        text = stringResource(resource = Res.string.register),
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.height(height = 30.dp))

                TextButton(
                    onClick = { onAction(WelcomeAction.OnGoToTermsAndPrivacyPolicy) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp)
                ) {
                    Text(
                        text = stringResource(resource = Res.string.by_continuing_you_agree_to_our_terms_and_privacy_policy),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )
                }
            }
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