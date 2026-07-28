package com.product.ui.createUser

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.product.data.local.entity.UserEntity
import com.product.data.model.NewUser
import com.product.data.repository.user.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateUserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userData = mutableStateOf(
        NewUser(
            name = "",
            mobileNumber = "",
            email = "",
            occupation = "",
            password = "",
            confirmPassword = "",
            userName = ""
        )
    )
    val userData: State<NewUser> = _userData

    var nameError by mutableStateOf<String?>(null)
        private set

    var userNameError by mutableStateOf<String?>(null)
        private set

    var mobileError by mutableStateOf<String?>(null)
        private set

    var emailError by mutableStateOf<String?>(null)
        private set

    var occupationError by mutableStateOf<String?>(null)
        private set

    var passwordError by mutableStateOf<String?>(null)
        private set

    var confirmPasswordError by mutableStateOf<String?>(null)
        private set

    fun onNameChange(value: String) {
        _userData.value = _userData.value.copy(name = value)
        validateName()
    }

    fun onUserNameChange(value: String) {
        _userData.value = _userData.value.copy(userName = value)
        validateUserName()
    }

    fun onEmailChange(value: String) {
        _userData.value = _userData.value.copy(email = value)
        validateEmail()
    }

    fun onOccupationChange(value: String) {
        _userData.value = _userData.value.copy(occupation = value)
        validateOccupation()
    }

    fun onPasswordChange(value: String) {
        _userData.value = _userData.value.copy(password = value)
        validatePassword()
        validateConfirmPassword()
    }

    fun onConfirmPasswordChange(value: String) {
        _userData.value = _userData.value.copy(confirmPassword = value)
        validateConfirmPassword()
    }

    fun onMobileChange(value: String) {
        _userData.value = _userData.value.copy(mobileNumber = value)
        validateMobile()
    }

    private fun validateName() {
        nameError = if (_userData.value.name.isBlank()) "Name is required" else null
    }

    private fun validateUserName() {
        userNameError = if (_userData.value.userName.isBlank()) "Username is required" else null
    }

    private fun validateEmail() {
        emailError = when {
            _userData.value.email.isBlank() -> "Email is required"
            !android.util.Patterns.EMAIL_ADDRESS.matcher(_userData.value.email).matches() -> "Invalid email"
            else -> null
        }
    }

    private fun validateOccupation() {
        occupationError = if (_userData.value.occupation.isBlank()) "Occupation is required" else null
    }

    private fun validateMobile() {
        val mobile = _userData.value.mobileNumber
        mobileError = when {
            mobile.isBlank() -> "Mobile number required"
            !mobile.matches(Regex("^[6-9]\\d{9}$")) -> "Enter valid 10-digit mobile number"
            else -> null
        }
    }

    private fun validatePassword() {
        passwordError = when {
            _userData.value.password.isBlank() -> "Password required"
            _userData.value.password.length < 6 -> "Password should contain minimum 6 characters"
            else -> null
        }
    }

    private fun validateConfirmPassword() {
        confirmPasswordError = when {
            _userData.value.confirmPassword.isBlank() -> "Confirm password required"
            _userData.value.password != _userData.value.confirmPassword -> "Password doesn't match"
            else -> null
        }
    }

    fun validateForm(): Boolean {
        validateName()
        validateUserName()
        validateMobile()
        validateEmail()
        validateOccupation()
        validatePassword()
        validateConfirmPassword()

        return nameError == null &&
                userNameError == null &&
                mobileError == null &&
                emailError == null &&
                occupationError == null &&
                passwordError == null &&
                confirmPasswordError == null
    }

    fun createUser(onSuccess: () -> Unit) {
        if (validateForm()) {
            viewModelScope.launch {
                val user = _userData.value
                val userEntity = UserEntity(
                    userName = user.userName,
                    name = user.name,
                    mobileNumber = user.mobileNumber,
                    email = user.email,
                    occupation = user.occupation,
                    password = user.password,
                    confirmPassword = user.confirmPassword
                )
                userRepository.insertUser(userEntity)
                
                // Fetch and print all users from Room DB (one-time fetch for logging)
                val allUsers = userRepository.getAllUsers().first()
                allUsers.forEach { 
                    android.util.Log.d("CreateUserViewModel", "Stored User: $it")
                }

                onSuccess()
            }
        }
    }
}
