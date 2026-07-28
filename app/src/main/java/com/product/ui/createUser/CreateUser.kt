package com.product.ui.createUser

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.product.ui.components.AppTopBar

@Composable
fun CreateUser(
    onBack: () -> Unit,
    onSuccess: () -> Unit,
    viewModel: CreateUserViewModel = hiltViewModel()
) {
    val user by viewModel.userData

    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Create User",
                headerText = "Register a new account",
                showBackButton = true,
                onBackClick = onBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Name
            OutlinedTextField(
                value = user.name,
                onValueChange = viewModel::onNameChange,
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = viewModel.nameError != null,
                supportingText = {
                    viewModel.nameError?.let { Text(it) }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )

            // Username
            OutlinedTextField(
                value = user.userName,
                onValueChange = viewModel::onUserNameChange,
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = viewModel.userNameError != null,
                supportingText = {
                    viewModel.userNameError?.let { Text(it) }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )

            // Mobile
            OutlinedTextField(
                value = user.mobileNumber,
                onValueChange = viewModel::onMobileChange,
                label = { Text("Mobile Number") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = viewModel.mobileError != null,
                supportingText = {
                    viewModel.mobileError?.let { Text(it) }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Next
                )
            )

            // Email
            OutlinedTextField(
                value = user.email,
                onValueChange = viewModel::onEmailChange,
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = viewModel.emailError != null,
                supportingText = {
                    viewModel.emailError?.let { Text(it) }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )

            // Occupation
            OutlinedTextField(
                value = user.occupation,
                onValueChange = viewModel::onOccupationChange,
                label = { Text("Occupation") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = viewModel.occupationError != null,
                supportingText = {
                    viewModel.occupationError?.let { Text(it) }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )

            // Password
            OutlinedTextField(
                value = user.password,
                onValueChange = viewModel::onPasswordChange,
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = viewModel.passwordError != null,
                supportingText = {
                    viewModel.passwordError?.let { Text(it) }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                )
            )

            // Confirm Password
            OutlinedTextField(
                value = user.confirmPassword,
                onValueChange = viewModel::onConfirmPasswordChange,
                label = { Text("Confirm Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = viewModel.confirmPasswordError != null,
                supportingText = {
                    viewModel.confirmPasswordError?.let { Text(it) }
                },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.createUser(onSuccess)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "Create User",
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
