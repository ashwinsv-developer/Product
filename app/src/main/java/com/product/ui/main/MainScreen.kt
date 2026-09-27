package com.product.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.product.navigation.Home
import com.product.navigation.Products
import com.product.ui.home.HomeScreen
import com.product.ui.products.ProductsScreen

@Composable
fun MainScreen(
    rootNavController: NavHostController,
    onLogout: () -> Unit
) {
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Home,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Home> {
                HomeScreen(
                    onLogout = onLogout,
                    onProductClick = { productId ->
                        // ProductDetail is in the rootNavController
                        rootNavController.navigate(com.product.navigation.ProductDetail(productId))
                    }
                )
            }
            composable<Products> {
                ProductsScreen(
                    onProductClick = { productId ->
                        rootNavController.navigate(com.product.navigation.ProductDetail(productId))
                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        val items = listOf(
            BottomNavItem("Home", Home, Icons.Default.Home),
            BottomNavItem("Products", Products, Icons.Default.List)
        )

        items.forEach { item ->
            val selected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true
            
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

data class BottomNavItem<T : Any>(
    val label: String,
    val route: T,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)
