package com.product.navigation


import kotlinx.serialization.Serializable

@Serializable
object Login

@Serializable
object Home

@Serializable
data class ProductDetail(
    val productId: Int
)
