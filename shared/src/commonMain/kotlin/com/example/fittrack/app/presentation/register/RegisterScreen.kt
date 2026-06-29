package com.example.fittrack.app.presentation.register

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
import fittrack.shared.generated.resources.confirm_password
import fittrack.shared.generated.resources.email
import fittrack.shared.generated.resources.email_already_exist
import fittrack.shared.generated.resources.email_can_not_be_blank
import fittrack.shared.generated.resources.email_is_not_valid
import fittrack.shared.generated.resources.first_name
import fittrack.shared.generated.resources.first_name_can_not_be_blank
import fittrack.shared.generated.resources.have_an_account
import fittrack.shared.generated.resources.hide_password
import fittrack.shared.generated.resources.last_name
import fittrack.shared.generated.resources.last_name_can_not_be_blank
import fittrack.shared.generated.resources.password
import fittrack.shared.generated.resources.password_and_confirm_password_are_not_the_same
import fittrack.shared.generated.resources.password_can_not_be_blank
import fittrack.shared.generated.resources.register
import fittrack.shared.generated.resources.show_password
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterRoot(
    onNavigateToLogin: () -> Unit,
    viewModel: RegisterViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(events = viewModel.events) { event ->
        when(event) {
            is RegisterEvent.OnRegister -> onNavigateToLogin()
        }
    }

    RegisterScreen(
        state = state,
        onAction = {action ->
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(resource = Res.string.register),
            fontSize = 30.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(height = 30.dp))

        OutlinedTextField(
            value = state.firstName,
            onValueChange = {
                onAction(RegisterAction.OnFirstNameValueChange(firstName = it))
            },
            singleLine = true,
            label = { Text(text = stringResource(resource = Res.string.first_name)) },
            modifier = Modifier
                .padding(all = 10.dp)
                .focusRequester(focusRequester = focusRequester),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(focusDirection = FocusDirection.Next)
                }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(size = 16.dp)
        )

        if (state.isFirstNameBlank) {
            Spacer(modifier = Modifier.padding(top = 5.dp))

            Text(
                text = stringResource(resource = Res.string.first_name_can_not_be_blank),
                color = MaterialTheme.colorScheme.error,
                fontSize = 15.sp
            )
        }

        OutlinedTextField(
            value = state.lastName,
            onValueChange = {
                onAction(RegisterAction.OnLastNameValueChange(lastName = it))
            },
            singleLine = true,
            label = { Text(text = stringResource(resource = Res.string.last_name)) },
            modifier = Modifier
                .padding(all = 10.dp)
                .focusRequester(focusRequester = focusRequester),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(focusDirection = FocusDirection.Next)
                }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(size = 16.dp)
        )

        if (state.isLastNameBlank) {
            Spacer(modifier = Modifier.padding(top = 5.dp))

            Text(
                text = stringResource(resource = Res.string.last_name_can_not_be_blank),
                color = MaterialTheme.colorScheme.error,
                fontSize = 15.sp
            )
        }

        OutlinedTextField(
            value = state.email,
            onValueChange = {
                onAction(RegisterAction.OnEmailValueChange(email = it))
            },
            singleLine = true,
            label = { Text(text = stringResource(resource = Res.string.email)) },
            modifier = Modifier
                .padding(all = 10.dp)
                .focusRequester(focusRequester),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(focusDirection = FocusDirection.Next)
                }
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary
            ),
            isError = !state.isEmailValid,
            shape = RoundedCornerShape(size = 16.dp)
        )

        if (state.isEmailBlank) {
            Spacer(modifier = Modifier.padding(top = 5.dp))

            Text(
                text = stringResource(resource = Res.string.email_can_not_be_blank),
                color = MaterialTheme.colorScheme.error,
                fontSize = 15.sp
            )
        }

        if (state.emailExists) {
            Spacer(modifier = Modifier.padding(top = 5.dp))

            Text(
                text = stringResource(resource = Res.string.email_already_exist)
            )
        }

        if (!state.isEmailValid) {
            Spacer(modifier = Modifier.padding(top = 5.dp))

            Text(
                text = stringResource(resource = Res.string.email_is_not_valid),
                color = MaterialTheme.colorScheme.error,
                fontSize = 15.sp
            )
        }

        OutlinedTextField(
            value = state.password,
            onValueChange = { onAction(RegisterAction.OnPasswordValueChange(password = it)) },
            singleLine = true,
            label = { Text(text = stringResource(resource = Res.string.password)) },
            modifier = Modifier
                .padding(all = 10.dp)
                .focusRequester(focusRequester),
            visualTransformation = if (!state.isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
            trailingIcon = {
                val icon = if (state.isPasswordVisible) Icons.Default.Lock else Icons.Default.CheckCircle
                val description = if (state.isPasswordVisible) stringResource(resource = Res.string.hide_password) else stringResource(resource = Res.string.show_password)
                IconButton(
                    onClick = { onAction(RegisterAction.OnTogglePasswordVisibility) }
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = description
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(size = 16.dp)
        )

        if (state.isPasswordBlank) {
            Spacer(modifier = Modifier.padding(top = 5.dp))

            Text(
                text = stringResource(resource = Res.string.password_can_not_be_blank),
                color = MaterialTheme.colorScheme.error,
                fontSize = 15.sp
            )
        }

        OutlinedTextField(
            value = state.confirmPassword,
            onValueChange = { onAction(RegisterAction.OnConfirmPasswordValueChange(confirmPassword = it)) },
            singleLine = true,
            label = { Text(text = stringResource(resource = Res.string.confirm_password)) },
            modifier = Modifier
                .padding(all = 10.dp)
                .focusRequester(focusRequester),
            visualTransformation = if (!state.isConfirmPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() }),
            trailingIcon = {
                val icon = if (state.isConfirmPasswordVisible) Icons.Default.Lock else Icons.Default.CheckCircle
                val description = if (state.isConfirmPasswordVisible) stringResource(resource = Res.string.hide_password) else stringResource(resource = Res.string.show_password)
                IconButton(
                    onClick = { onAction(RegisterAction.OnToggleConfirmPasswordVisibility) }
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = description
                    )
                }
            },
            isError = !state.isPasswordAndConfirmPasswordAreEqual,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(size = 16.dp)
        )

        if (!state.isPasswordAndConfirmPasswordAreEqual) {
            Spacer(modifier = Modifier.padding(top = 10.dp))

            Text(
                text = stringResource(resource = Res.string.password_and_confirm_password_are_not_the_same),
                color = MaterialTheme.colorScheme.error,
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.height(height = 40.dp))

        TextButton(
            onClick = { onAction(RegisterAction.OnRegister) },
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
                text = stringResource(resource = Res.string.register),
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(height = 10.dp))


        TextButton(
            onClick = {onAction(RegisterAction.OnGoToLogin)}
        ) {
            Text(
                text = stringResource(resource = Res.string.have_an_account),
                fontSize = 16.sp
            )
        }
    }
}

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