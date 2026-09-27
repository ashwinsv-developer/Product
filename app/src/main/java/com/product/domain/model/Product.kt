package com.product.domain.model

/**
 * Domain model representing a Product.
 * This class is pure Kotlin and completely decoupled from any specific data layer representations or serialization annotations.
 */
data class Product(
    val id: String,
    val name: String,
    val price: Double
)
