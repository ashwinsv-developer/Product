package com.product.ui.login

import com.product.data.SessionManager
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private val sessionManager: SessionManager = mockk(relaxed = true)

    @Before
    fun setup() {
        viewModel = LoginViewModel(sessionManager)
    }

    @Test
    fun `initial state is correct`() {
        assertEquals("", viewModel.email)
        assertEquals("", viewModel.password)
        assertNull(viewModel.emailError)
        assertNull(viewModel.passwordError)
        assertFalse(viewModel.isSuccess)
    }

    @Test
    fun `onEmailChange updates email and clears error`() {
        viewModel.onEmailChange("test@example.com")
        assertEquals("test@example.com", viewModel.email)
        assertNull(viewModel.emailError)
    }

    @Test
    fun `onPasswordChange updates password and clears error`() {
        viewModel.onPasswordChange("password123")
        assertEquals("password123", viewModel.password)
        assertNull(viewModel.passwordError)
    }

    @Test
    fun `login with empty email and password sets errors`() {
        viewModel.login()

        assertEquals("Email cannot be empty", viewModel.emailError)
        assertEquals("Password cannot be empty", viewModel.passwordError)
        assertFalse(viewModel.isSuccess)
    }

    @Test
    fun `login with invalid email format sets error`() {
        viewModel.onEmailChange("invalid-email")
        viewModel.onPasswordChange("password123")
        
        viewModel.login()
        
        assertEquals("Invalid email format", viewModel.emailError)
        assertFalse(viewModel.isSuccess)
    }

    @Test
    fun `login with short password sets error`() {
        viewModel.onEmailChange("valid@email.com")
        viewModel.onPasswordChange("short")
        
        viewModel.login()
        
        assertEquals("Password must be at least 8 characters", viewModel.passwordError)
        assertFalse(viewModel.isSuccess)
    }

    @Test
    fun `login with valid credentials calls sessionManager and sets success`() {
        val validEmail = "test@example.com"
        viewModel.onEmailChange(validEmail)
        viewModel.onPasswordChange("password123")
        
        viewModel.login()
        
        verify { sessionManager.saveSession(validEmail) }
        assertTrue(viewModel.isSuccess)
        assertNull(viewModel.emailError)
        assertNull(viewModel.passwordError)
    }
}
