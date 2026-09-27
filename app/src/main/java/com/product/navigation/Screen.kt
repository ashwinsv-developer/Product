package com.product.navigation

import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Main

@Serializable
object Home

@Serializable
object Products

@Serializable
object CreateUser

@Serializable
data class ProductDetail(
    val productId: Int
)
