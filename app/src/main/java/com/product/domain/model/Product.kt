package com.product.domain.model

/**
 * Domain model representing a Product.
 * This class is pure Kotlin and completely decoupled from any specific data layer representations or serialization annotations.
 */
data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String,
    val category: String,
    val thumbnail: String,
    val images: List<String>
)
