package com.example.fittrack.terms_and_privacy_policy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fittrack.core.presentation.ObserveAsEvent
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.arrow_back
import fittrack.shared.generated.resources.terms_and_privacy_policy
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TermsAndPrivacyPolicyRoot(
    viewModel: TermsAndPrivacyPolicyViewModel = koinViewModel(),
    onNavigateBack: () -> Unit
) {
    ObserveAsEvent(events = viewModel.events) { event ->
        when(event) {
            is TermsAndPrivacyPolicyEvent.OnGoBack -> onNavigateBack()
        }
    }

    TermsAndPrivacyPolicyScreen(
        onAction = { action ->
            viewModel.onAction(action = action)
        }
    )
}

@Composable
fun TermsAndPrivacyPolicyScreen(
    onAction: (TermsAndPrivacyPolicyAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(resource = Res.string.terms_and_privacy_policy),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .size(size = 48.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primaryContainer
                            )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(resource = Res.string.arrow_back),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.background,
                    actionIconContentColor = MaterialTheme.colorScheme.background,
                    subtitleContentColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(state = rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(height = 24.dp))

            Row {

            }
        }
    }
}