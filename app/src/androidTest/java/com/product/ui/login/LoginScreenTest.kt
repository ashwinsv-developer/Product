package com.product.ui.login

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.platform.app.InstrumentationRegistry
import com.product.data.SessionManager
import com.product.util.Constants
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var sessionManager: SessionManager
    private var loginSuccessCalled = false
    private val onLoginSuccess: () -> Unit = { loginSuccessCalled = true }

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        sessionManager = SessionManager(context)
        sessionManager.clearSession()
        loginSuccessCalled = false
    }

    @Test
    fun loginScreen_InitialState_DisplaysCorrectUI() {
        composeTestRule.setContent {
            LoginScreen(
                onLoginSuccess = onLoginSuccess,
                viewModel = LoginViewModel(sessionManager)
            )
        }

        composeTestRule.onNodeWithText(Constants.WELCOME).assertIsDisplayed()
        composeTestRule.onNodeWithText(Constants.EMAIL).assertIsDisplayed()
        composeTestRule.onNodeWithText(Constants.PASSWORD).assertIsDisplayed()
        composeTestRule.onNodeWithText(Constants.LOGIN).assertIsDisplayed()
    }

    @Test
    fun loginScreen_InputCredentials_UpdatesFields() {
        composeTestRule.setContent {
            LoginScreen(
                onLoginSuccess = onLoginSuccess,
                viewModel = LoginViewModel(sessionManager)
            )
        }

        val email = "user@example.com"
        val password = "password123"

        composeTestRule.onNodeWithText(Constants.EMAIL).performTextInput(email)
        composeTestRule.onNodeWithText(Constants.PASSWORD).performTextInput(password)

        composeTestRule.onNodeWithText(email).assertExists()
    }

    @Test
    fun loginScreen_EmptyLogin_ShowsErrorMessages() {
        composeTestRule.setContent {
            LoginScreen(
                onLoginSuccess = onLoginSuccess,
                viewModel = LoginViewModel(sessionManager)
            )
        }

        composeTestRule.onNodeWithText(Constants.LOGIN).performClick()

        composeTestRule.onNodeWithText("Email cannot be empty").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password cannot be empty").assertIsDisplayed()
    }

    @Test
    fun loginScreen_SuccessfulLogin_CallsCallback() {
        composeTestRule.setContent {
            LoginScreen(
                onLoginSuccess = onLoginSuccess,
                viewModel = LoginViewModel(sessionManager)
            )
        }

        val email = "user@example.com"
        val password = "password123"

        composeTestRule.onNodeWithText(Constants.EMAIL).performTextInput(email)
        composeTestRule.onNodeWithText(Constants.PASSWORD).performTextInput(password)
        composeTestRule.onNodeWithText(Constants.LOGIN).performClick()

        assert(loginSuccessCalled)
        assert(sessionManager.isLoggedIn())
        assert(sessionManager.getEmail() == email)
    }
}
