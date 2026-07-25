package com.product.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.product.data.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val sessionManager: SessionManager
) : ViewModel() {

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    var emailError by mutableStateOf<String?>(null)
        private set

    var passwordError by mutableStateOf<String?>(null)
        private set

    var isSuccess by mutableStateOf(false)
        private set

    fun onEmailChange(newEmail: String) {
        email = newEmail
        emailError = null
    }

    fun onPasswordChange(newPassword: String) {
        password = newPassword
        passwordError = null
    }

    fun login() {
        val isEmailValid = validateEmail(email)
        val isPasswordValid = validatePassword(password)

        if (isEmailValid && isPasswordValid) {
            sessionManager.saveSession(email)
            isSuccess = true
        }
    }

    private fun validateEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()
        return if (email.isNotEmpty() && email.matches(emailRegex)) {
            true
        } else if (email.isEmpty()) {
            emailError = "Email cannot be empty"
            false
        } else {
            emailError = "Invalid email format"
            false
        }
    }

    private fun validatePassword(password: String): Boolean {
        return when {
            password.isEmpty() -> {
                passwordError = "Password cannot be empty"
                false
            }
            password.length < 8 -> {
                passwordError = "Password must be at least 8 characters"
                false
            }
            else -> true
        }
    }
}
