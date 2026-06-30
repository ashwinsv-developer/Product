package com.product

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.product.data.SessionManager
import com.product.navigation.AppNavGraph
import com.product.navigation.Home
import com.product.navigation.Login
import com.product.ui.theme.ProductTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var sessionManager: SessionManager
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ProductTheme {

                val navController = rememberNavController()

                val startDestination =
                    if (sessionManager.isLoggedIn()) {
                        Home
                    } else {
                        Login
                    }

                AppNavGraph(
                    navController = navController,
                    startDestination = startDestination
                )
            }
        }
    }
}