package com.product.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.product.ui.detail.ProductDetailScreen
import com.product.ui.home.HomeScreen
import com.product.ui.login.LoginScreen
import com.product.ui.createUser.CreateUser
import com.product.ui.main.MainScreen

@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: Any
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable<Login> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Main) {
                        popUpTo<Login> {
                            inclusive = true
                        }
                    }
                },
                onNavigateToCreateUser = {
                    navController.navigate(CreateUser)
                }
            )
        }

        composable<CreateUser> {
            CreateUser(
                onBack = {
                    navController.popBackStack()
                },
                onSuccess = {
                    navController.navigate(Main) {
                        popUpTo<CreateUser> {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Main> {
            MainScreen(
                rootNavController = navController,
                onLogout = {
                    navController.navigate(Login) {
                        popUpTo<Main> {
                            inclusive = true
                        }
                    }
                }
            )
        }


        composable<ProductDetail> { backStackEntry ->

            val args = backStackEntry.toRoute<ProductDetail>()

            ProductDetailScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}