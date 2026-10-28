package com.example.fittrack.app.presentation.register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fittrack.core.presentation.ObserveAsEvent
import com.example.fittrack.welcome_page.presentation.components.ErrorOutlinedTextField
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.apple
import fittrack.shared.generated.resources.arrow_back
import fittrack.shared.generated.resources.confirm_password
import fittrack.shared.generated.resources.create_account
import fittrack.shared.generated.resources.email
import fittrack.shared.generated.resources.email_can_not_be_blank
import fittrack.shared.generated.resources.first_name
import fittrack.shared.generated.resources.first_name_can_not_be_blank
import fittrack.shared.generated.resources.fitrack
import fittrack.shared.generated.resources.google
import fittrack.shared.generated.resources.have_an_account
import fittrack.shared.generated.resources.hero_rings
import fittrack.shared.generated.resources.hide_password
import fittrack.shared.generated.resources.last_name
import fittrack.shared.generated.resources.last_name_can_not_be_blank
import fittrack.shared.generated.resources.lock
import fittrack.shared.generated.resources.login
import fittrack.shared.generated.resources.or_continue_with
import fittrack.shared.generated.resources.password
import fittrack.shared.generated.resources.password_and_confirm_password_are_not_the_same
import fittrack.shared.generated.resources.password_can_not_be_blank
import fittrack.shared.generated.resources.register
import fittrack.shared.generated.resources.show_password
import fittrack.shared.generated.resources.sign_up_to_start_your_training
import fittrack.shared.generated.resources.social_media
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterRoot(
    onNavigateToLogin: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(events = viewModel.events) { event ->
        when(event) {
            is RegisterEvent.OnRegister -> onNavigateToLogin()
            is RegisterEvent.OnGoBack -> onNavigateBack()
        }
    }

    RegisterScreen(
        state = state,
        onAction = { action ->
            viewModel.onAction(action = action)

            when (action) {
                is RegisterAction.OnGoToLogin -> onNavigateToLogin()
                else -> Unit
            }
        }
    )
}

@Composable
fun RegisterScreen(
    state: RegisterState,
    onAction: (RegisterAction) -> Unit
) {
    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { onAction(RegisterAction.OnGoBack) },
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(size = 45.dp),
                        colors = IconButtonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            disabledContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            disabledContentColor =  MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(resource = Res.string.arrow_back),
                            modifier = Modifier.size(size = 27.dp)
                        )
                    }
                },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                    navigationIconContentColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.background,
                    subtitleContentColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(resource = Res.string.have_an_account),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                TextButton(
                    onClick = { onAction(RegisterAction.OnGoToLogin) }
                ) {
                    Text(
                        text = stringResource(resource = Res.string.login),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(height = 70.dp))
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .padding(horizontal = 20.dp)
                .background(color = MaterialTheme.colorScheme.background.copy(alpha = 0.9f)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .weight(weight = 1f)
                    .verticalScroll(state = rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(height = 12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocalFireDepartment,
                        contentDescription = stringResource(resource = Res.string.hero_rings),
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(size = 22.dp)
                    )

                    Spacer(modifier = Modifier.width(width = 6.dp))

                    Text(
                        text = stringResource(resource = Res.string.fitrack),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                Spacer(modifier = Modifier.height(height = 12.dp))

                Text(
                    text = stringResource(resource = Res.string.create_account),
                    fontSize = 32.sp,
                    lineHeight = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(height = 10.dp))

                Text(
                    text = stringResource(resource = Res.string.sign_up_to_start_your_training),
                    fontSize = 18.sp,
                    lineHeight = 21.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(height = 24.dp))

                ErrorOutlinedTextField(
                    value = state.firstName,
                    onValueChange = { onAction(RegisterAction.OnFirstNameValueChange(firstName = it)) },
                    placeholder = stringResource(resource = Res.string.first_name),
                    leadingIcon = Icons.Outlined.Person,
                    errorMessage = if (state.isFirstNameBlank) stringResource(resource = Res.string.first_name_can_not_be_blank) else null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Next) }
                    ),
                    modifier = Modifier.focusRequester(focusRequester = focusRequester)
                )

                Spacer(modifier = Modifier.height(height = 16.dp))

                ErrorOutlinedTextField(
                    value = state.lastName,
                    onValueChange = { onAction(RegisterAction.OnLastNameValueChange(lastName = it)) },
                    placeholder = stringResource(resource = Res.string.last_name),
                    leadingIcon = Icons.Outlined.Person,
                    errorMessage = if (state.isLastNameBlank) stringResource(resource = Res.string.last_name_can_not_be_blank) else null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Next) }
                    ),
                    modifier = Modifier.focusRequester(focusRequester = focusRequester)
                )

                Spacer(modifier = Modifier.height(height = 16.dp))

                ErrorOutlinedTextField(
                    value = state.email,
                    onValueChange = { onAction(RegisterAction.OnEmailValueChange(email = it)) },
                    placeholder = stringResource(resource = Res.string.email),
                    leadingIcon = Icons.Outlined.Email,
                    errorMessage = if (state.isEmailBlank) stringResource(resource = Res.string.email_can_not_be_blank) else null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Next) }
                    ),
                    modifier = Modifier.focusRequester(focusRequester = focusRequester)
                )

                Spacer(modifier = Modifier.height(height = 16.dp))

                ErrorOutlinedTextField(
                    value = state.password,
                    onValueChange = { onAction(RegisterAction.OnPasswordValueChange(password = it)) },
                    placeholder = stringResource(resource = Res.string.password),
                    leadingIcon = Icons.Outlined.Lock,
                    trailingIcon = {
                        IconButton(
                            onClick = { onAction(RegisterAction.OnTogglePasswordVisibility) }
                        ) {
                            Icon(
                                imageVector = if (state.isPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                contentDescription = if (state.isPasswordVisible) stringResource(resource = Res.string.show_password) else stringResource(resource = Res.string.hide_password)
                            )
                        }
                    },
                    errorMessage = if (state.isPasswordBlank) stringResource(resource = Res.string.password_can_not_be_blank) else null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(focusDirection = FocusDirection.Next) }
                    ),
                    modifier = Modifier.focusRequester(focusRequester = focusRequester),
                    isPasswordVisible = state.isPasswordVisible
                )

                Spacer(modifier = Modifier.height(height = 16.dp))

                ErrorOutlinedTextField(
                    value = state.confirmPassword,
                    onValueChange = { onAction(RegisterAction.OnConfirmPasswordValueChange(confirmPassword = it)) },
                    placeholder = stringResource(resource = Res.string.confirm_password),
                    leadingIcon = Icons.Outlined.Lock,
                    trailingIcon = {
                        IconButton(
                            onClick = { onAction(RegisterAction.OnToggleConfirmPasswordVisibility) }
                        ) {
                            Icon(
                                imageVector = if (state.isConfirmPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                contentDescription = if (state.isConfirmPasswordVisible) stringResource(resource = Res.string.show_password) else stringResource(resource = Res.string.hide_password)
                            )
                        }
                    },
                    errorMessage = if (!state.arePasswordAndConfirmPasswordEqual) stringResource(resource = Res.string.password_and_confirm_password_are_not_the_same) else null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.clearFocus() }
                    ),
                    modifier = Modifier.focusRequester(focusRequester = focusRequester),
                    isPasswordVisible = state.isConfirmPasswordVisible
                )

                Spacer(modifier = Modifier.height(height = 20.dp))

                Button(
                    onClick = { onAction(RegisterAction.OnRegister) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 48.dp),
                    shape = RoundedCornerShape(size = 24.dp)
                ) {
                    Text(
                        text = stringResource(resource = Res.string.register),
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(height = 24.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(weight = 1f),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )

                    Text(
                        text = stringResource(resource = Res.string.or_continue_with),
                        modifier = Modifier.padding(horizontal = 12.dp),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    HorizontalDivider(
                        modifier = Modifier.weight(weight = 1f),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }

                Spacer(modifier = Modifier.height(height = 16.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(space = 12.dp)
                ) {
                    SocialButton(
                        text = stringResource(resource = Res.string.google),
                        icon = painterResource(resource = Res.drawable.google),
                        onClick = {  },
                        modifier = Modifier.weight(weight = 1f)
                    )

                    SocialButton(
                        text = stringResource(resource = Res.string.apple),
                        icon = painterResource(resource = Res.drawable.apple),
                        onClick = {  },
                        modifier = Modifier.weight(weight = 1f)
                    )

                    Spacer(modifier = Modifier.height(height = 16.dp))
                }
            }
        }
    }
}

@Composable
private fun SocialButton(
    text: String,
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = { onClick() },
        modifier = modifier.height(height = 52.dp),
        shape = RoundedCornerShape(size = 24.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Icon(
            painter = icon,
            contentDescription = stringResource(resource = Res.string.social_media),
            modifier = Modifier.size(size = 22.dp)
        )

        Spacer(modifier = Modifier.width(width = 8.dp))

        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun registerFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = MaterialTheme.colorScheme.primary,
    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
    focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
    unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
    focusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
    unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
    focusedTextColor = MaterialTheme.colorScheme.onBackground,
    unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
    focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
    unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
    cursorColor = MaterialTheme.colorScheme.primary
)

@Preview
@Composable
fun RegisterScreenPreview() {
    MaterialTheme {
        RegisterScreen(
            state = RegisterState(),
            onAction = {}
        )
    }
}