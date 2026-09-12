package com.product.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.product.util.Constants
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.map


@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToCreateUser: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val email = viewModel.email
    val password = viewModel.password
    val emailError = viewModel.emailError
    val passwordError = viewModel.passwordError
    val isSuccess = viewModel.isSuccess

    // Using snapshotFlow to observe email changes reactively
    LaunchedEffect(Unit) {
        snapshotFlow { viewModel.email }
            .collect { _ ->
                // You can perform side effects or reactive validation here
            }
    }

    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge,
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 8.dp
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = Constants.WELCOME,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Sign in to continue",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(
                    modifier = Modifier.height(32.dp)
                )

                val processedEmail by remember(viewModel) {
                    snapshotFlow { viewModel.email }.map { it.lowercase() }
                }.collectAsState(initial = viewModel.email)

                OutlinedTextField(
                    value = processedEmail,
                    onValueChange = {
                        viewModel.onEmailChange(it)
                    },
                    label = {
                        Text(Constants.EMAIL)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    isError = emailError != null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    supportingText = {
                        emailError?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )

                Spacer(
                    modifier = Modifier.height(16.dp)
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        viewModel.onPasswordChange(it)
                    },
                    label = {
                        Text(Constants.PASSWORD)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation =
                        PasswordVisualTransformation(),
                    singleLine = true,
                    isError = passwordError != null,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    supportingText = {
                        passwordError?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )

                Spacer(
                    modifier = Modifier.height(32.dp)
                )


                Button(
                    onClick = {
                        viewModel.login()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6C3BFF),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = Constants.LOGIN,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(   verticalAlignment = Alignment.CenterVertically) {

            Text(

                text = Constants.new_user_login,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )


            TextButton(onClick = { onNavigateToCreateUser() }) {
                Text(
                    text = Constants.signIn,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6C3BFF)
                )
            }
        }
        }
    }
}
