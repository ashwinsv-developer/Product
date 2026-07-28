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
                    navController.navigate(Home) {
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
                    navController.navigate(Home) {
                        popUpTo<CreateUser> {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable<Home> {
            HomeScreen(
                onLogout = {
                    navController.navigate(Login) {
                        popUpTo<Home> {
                            inclusive = true
                        }
                    }
                },
                onProductClick = { productId ->
                    navController.navigate(
                        ProductDetail(productId)
                    )
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