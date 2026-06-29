package com.example.fittrack.app.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fittrack.core.presentation.ObserveAsEvent
import fittrack.shared.generated.resources.Res
import fittrack.shared.generated.resources.dont_have_an_account
import fittrack.shared.generated.resources.email
import fittrack.shared.generated.resources.email_ot_password_is_invalid
import fittrack.shared.generated.resources.hide_password
import fittrack.shared.generated.resources.login
import fittrack.shared.generated.resources.password
import fittrack.shared.generated.resources.show_password
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            .background(color = MaterialTheme.colorScheme.onPrimary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(resource = Res.string.login),
            fontSize = 40.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(70.dp))

        OutlinedTextField(
            value = state.email,
            onValueChange = { onAction(LoginAction.OnEmailValueChange(email = it)) },
            singleLine = true,
            label = { Text(text = stringResource(resource = Res.string.email)) },
            modifier = Modifier
                .padding(all = 10.dp)
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Next) }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(size = 16.dp)
        )

        Spacer(modifier = Modifier.height(height = 20.dp))

        OutlinedTextField(
            value = state.password,
            onValueChange = { onAction(LoginAction.OnPasswordValueChange(password = it)) },
            singleLine = true,
            label = { Text(text = stringResource(resource = Res.string.password)) },
            modifier = Modifier
                .padding(all = 10.dp)
                .focusRequester(focusRequester = focusRequester),
            visualTransformation = if (!state.isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
            trailingIcon = {
                val icon = if (state.isPasswordVisible) Icons.Default.Lock else Icons.Default.CheckCircle
                val description = if (state.isPasswordVisible) stringResource(resource = Res.string.hide_password) else stringResource(resource = Res.string.show_password)
                IconButton(
                    onClick = { onAction(LoginAction.OnTogglePasswordVisibility) }
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = description
                    )
                }
            },
            shape = RoundedCornerShape(size = 16.dp)
        )

        Spacer(modifier = Modifier.height(height = 20.dp))

        TextButton(
            onClick = { onAction(LoginAction.OnLogin) },
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(size = 16.dp)
                )
                .width(width = 130.dp),
            colors = ButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = Color.Unspecified,
                disabledContentColor = Color.Unspecified
            )
        ) {
            Text(
                text = stringResource(resource = Res.string.login),
                fontSize = 16.sp
            )
        }

        if (state.badCredentials) {
            Spacer(modifier = Modifier.height(height = 30.dp))

            Text(
                text = stringResource(resource = Res.string.email_ot_password_is_invalid),
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(height = 30.dp))

        TextButton(
            onClick = {onAction(LoginAction.OnGoToRegister)}
        ) {
            Text(
                text = stringResource(resource = Res.string.dont_have_an_account),
                fontSize = 16.sp
            )
        }
    }
}

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