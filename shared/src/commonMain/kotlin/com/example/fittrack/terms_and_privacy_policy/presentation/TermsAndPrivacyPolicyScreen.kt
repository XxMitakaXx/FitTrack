package com.example.fittrack.terms_and_privacy_policy.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fittrack.core.presentation.ObserveAsEvent
import com.example.fittrack.terms_and_privacy_policy.domain.LegalSection
import com.example.fittrack.terms_and_privacy_policy.presentation.components.SlidingSegmentedControl
import com.example.fittrack.terms_and_privacy_policy.presentation.components.TermsAndPrivacyCenterAlignedTopAppBar
import com.example.fittrack.terms_and_privacy_policy.presentation.components.TermsAndPrivacySection
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.arrow_back
import fittrack.shared.generated.resources.privacy
import fittrack.shared.generated.resources.terms
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
    val termsSections = listOf(
        LegalSection("1. Your rights", "You own your workout data and can export or delete it at any time. You may use Pulse for personal, non-commercial purposes."),
        LegalSection(
            "2. Acceptable use",
            "Do not misuse the app, share your account, or try to access other users' data."
        ),
        LegalSection("3. Health notice", "Pulse offers general fitness guidance, not medical advice. Check with a doctor before starting a new program."),
        LegalSection("4. Changes", "We may update these terms. We will tell you in the app when that happens.")
    )

    val privacySections = listOf(
        LegalSection("1. Data we collect", "Your profile details, workouts, and activity data you choose to log."),
        LegalSection("2. How we use it", "To build your plans, show your progress, and improve the app."),
        LegalSection("3. Your choices", "You can change permissions, export your data, or delete your account in Settings.")
    )

    var selectedTab by remember { mutableIntStateOf(value = 0) }
    val selections = if (selectedTab == 0) termsSections else privacySections

    Scaffold(
        topBar = {
            TermsAndPrivacyCenterAlignedTopAppBar(
                onGoBack = { onAction(TermsAndPrivacyPolicyAction.OnGoBack) }
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .padding(horizontal = 20.dp)
                .verticalScroll(state = rememberScrollState())
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Spacer(modifier = Modifier.height(height = 20.dp))

            SlidingSegmentedControl(
                options = listOf(
                    stringResource(resource = Res.string.terms),
                    stringResource(resource = Res.string.privacy)
                ),
                selectedIndex = selectedTab,
                onSelected = { selectedTab = it }
            )

            Spacer(modifier = Modifier.height(height = 14.dp))

            Text(
                text = "Last updated October 15, 2026",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(height = 14.dp))

            TermsAndPrivacySection(
                modifier = Modifier.weight(weight = 1f),
                selections = selections
            )
        }
    }
}