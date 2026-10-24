package com.example.fittrack.app.presentation.login

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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.graphics.Color
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
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.apple
import fittrack.shared.generated.resources.arrow_back
import fittrack.shared.generated.resources.create_account
import fittrack.shared.generated.resources.dont_have_an_account
import fittrack.shared.generated.resources.email
import fittrack.shared.generated.resources.email_ot_password_is_invalid
import fittrack.shared.generated.resources.fitrack
import fittrack.shared.generated.resources.forgot_password
import fittrack.shared.generated.resources.google
import fittrack.shared.generated.resources.hero_rings
import fittrack.shared.generated.resources.hide_password
import fittrack.shared.generated.resources.lock
import fittrack.shared.generated.resources.log_in_to_continue_your_training
import fittrack.shared.generated.resources.login
import fittrack.shared.generated.resources.or_continue_with
import fittrack.shared.generated.resources.password
import fittrack.shared.generated.resources.show_password
import fittrack.shared.generated.resources.social_media
import fittrack.shared.generated.resources.welcome_back
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginRoot(
    onNavigateToTrainingScreen: () -> Unit,
    onNavigateToRegister: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(events = viewModel.events) { event ->
        when(event) {
            is LoginEvent.OnSuccessfulLogin -> onNavigateToTrainingScreen()
            else -> Unit
        }
    }

    LoginScreen(
        state = state,
        onAction = { action ->
            viewModel.onAction(action)

            when(action) {
                is LoginAction.OnGoToRegister -> onNavigateToRegister()
                else -> Unit
            }
        }
    )
}

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.primary,
        unfocusedTextColor = MaterialTheme.colorScheme.primary,
        focusedBorderColor = MaterialTheme.colorScheme.primary,
        unfocusedBorderColor = MaterialTheme.colorScheme.primary,
        focusedPlaceholderColor = MaterialTheme.colorScheme.primary
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {},
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
                Spacer(modifier = Modifier.height(height = 8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocalFireDepartment,
                        contentDescription = stringResource(resource = Res.string.hero_rings),
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(size = 18.dp)
                    )

                    Spacer(modifier = Modifier.width(width = 6.dp))

                    Text(
                        text = stringResource(resource = Res.string.fitrack),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                Spacer(modifier = Modifier.height(height = 8.dp))

                Text(
                    text = stringResource(resource = Res.string.welcome_back),
                    fontSize = 28.sp,
                    lineHeight = 34.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(height = 6.dp))

                Text(
                    text = stringResource(resource = Res.string.log_in_to_continue_your_training),
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(height = 24.dp))

                OutlinedTextField(
                    value = state.email,
                    onValueChange = { onAction(LoginAction.OnEmailValueChange(email = it)) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = stringResource(resource = Res.string.email)) },
                    leadingIcon = { Icon(imageVector = Icons.Outlined.Email, contentDescription = stringResource(resource = Res.string.email)) },
                    singleLine = true,
                    shape = RoundedCornerShape(size = 16.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    colors = loginFieldColors()
                )

                Spacer(modifier = Modifier.height(height = 12.dp))

                OutlinedTextField(
                    value = state.password,
                    onValueChange = { onAction(LoginAction.OnPasswordValueChange(password = it)) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(text = stringResource(resource = Res.string.password)) },
                    leadingIcon = { Icon(imageVector = Icons.Outlined.Lock, contentDescription = stringResource(resource = Res.string.lock)) },
                    trailingIcon = {
                        IconButton(
                            onClick = { onAction(LoginAction.OnTogglePasswordVisibility) }
                        ) {
                            Icon(
                                imageVector = if (state.isPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                contentDescription = if (state.isPasswordVisible) stringResource(resource = Res.string.show_password) else stringResource(resource = Res.string.hide_password)
                            )
                        }
                    },
                    visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    singleLine = true,
                    shape = RoundedCornerShape(size = 16.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    colors = loginFieldColors()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = {},
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text(
                            text = stringResource(resource = Res.string.forgot_password),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(height = 4.dp))

                Button(
                    onClick = { onAction(LoginAction.OnLogin) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 48.dp),
                    shape = RoundedCornerShape(size = 24.dp)
                ) {
                    Text(
                        text = stringResource(resource = Res.string.login),
                        fontSize = 15.sp,
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
                        fontSize = 12.sp,
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(resource = Res.string.dont_have_an_account),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    TextButton(
                        onClick = { onAction(LoginAction.OnGoToRegister) }
                    ) {
                        Text(
                            text = stringResource(resource = Res.string.create_account),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }


//            Text(
//                text = stringResource(resource = Res.string.login),
//                fontSize = 40.sp,
//                fontWeight = FontWeight.Medium,
//                color = MaterialTheme.colorScheme.primary
//            )
//
//            Spacer(modifier = Modifier.height(70.dp))
//
//            OutlinedTextField(
//                value = state.email,
//                onValueChange = { onAction(LoginAction.OnEmailValueChange(email = it)) },
//                singleLine = true,
//                label = { Text(text = stringResource(resource = Res.string.email)) },
//                modifier = Modifier
//                    .padding(all = 10.dp)
//                    .focusRequester(focusRequester = focusRequester),
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Email,
//                    imeAction = ImeAction.Next
//                ),
//                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
//                colors = textFieldColors,
//                shape = RoundedCornerShape(size = 16.dp)
//            )
//
//            Spacer(modifier = Modifier.height(height = 20.dp))
//
//            OutlinedTextField(
//                value = state.password,
//                onValueChange = { onAction(LoginAction.OnPasswordValueChange(password = it)) },
//                singleLine = true,
//                label = { Text(text = stringResource(resource = Res.string.password)) },
//                modifier = Modifier
//                    .padding(all = 10.dp)
//                    .focusRequester(focusRequester = focusRequester),
//                visualTransformation = if (!state.isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
//                keyboardOptions = KeyboardOptions(
//                    keyboardType = KeyboardType.Password,
//                    imeAction = ImeAction.Done
//                ),
//                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
//                colors = textFieldColors,
//                trailingIcon = {
//                    val icon = if (state.isPasswordVisible) Icons.Default.Lock else Icons.Default.CheckCircle
//                    val description = if (state.isPasswordVisible) stringResource(resource = Res.string.hide_password) else stringResource(resource = Res.string.show_password)
//                    IconButton(
//                        onClick = { onAction(LoginAction.OnTogglePasswordVisibility) }
//                    ) {
//                        Icon(
//                            imageVector = icon,
//                            contentDescription = description
//                        )
//                    }
//                },
//                shape = RoundedCornerShape(size = 16.dp)
//            )
//
//            Spacer(modifier = Modifier.height(height = 20.dp))
//
//            TextButton(
//                onClick = { onAction(LoginAction.OnLogin) },
//                modifier = Modifier.width(width = 130.dp),
//                colors = ButtonColors(
//                    containerColor = MaterialTheme.colorScheme.primary,
//                    contentColor = MaterialTheme.colorScheme.onPrimary,
//                    disabledContainerColor = Color.Unspecified,
//                    disabledContentColor = Color.Unspecified
//                )
//            ) {
//                Text(
//                    text = stringResource(resource = Res.string.login),
//                    fontSize = 16.sp
//                )
//            }
//
//            if (state.badCredentials) {
//                Spacer(modifier = Modifier.height(height = 30.dp))
//
//                Text(
//                    text = stringResource(resource = Res.string.email_ot_password_is_invalid),
//                    color = MaterialTheme.colorScheme.error
//                )
//            }
//
//            Spacer(modifier = Modifier.height(height = 30.dp))
//
//            TextButton(
//                onClick = {onAction(LoginAction.OnGoToRegister)}
//            ) {
//                Text(
//                    text = stringResource(resource = Res.string.dont_have_an_account),
//                    fontSize = 16.sp
//                )
//            }
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
        modifier = modifier.height(height = 48.dp),
        shape = RoundedCornerShape(size = 24.dp),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Icon(
            painter = icon,
            contentDescription = stringResource(resource = Res.string.social_media),
            modifier = Modifier.size(size = 18.dp)
        )

        Spacer(modifier = Modifier.width(width = 8.dp))

        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun loginFieldColors() = OutlinedTextFieldDefaults.colors(
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
fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            state = LoginState(),
            onAction = {}
        )
    }
}