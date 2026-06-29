package com.product.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val statusCode: Int,
    val data: ProductData,
    val message: String = "",
    val success: Boolean = true
)

@Serializable
data class ProductData(
    val page: Int,
    val limit: Int,
    val totalPages: Int,
    val previousPage: Boolean,
    val nextPage: Boolean,
    val totalItems: Int,
    val currentPageItems: Int,
    val data: List<Product>
)

@Serializable
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

@Serializable
data class SingleProductResponse(
    val statusCode: Int,
    val data: Product,
    val message: String,
    val success: Boolean
)
