package com.product.domain.repository

import com.product.domain.model.Product

/**
 * Domain-level repository interface for Product details.
 */
interface ProductDetailRepository {
    suspend fun getProductDetails(productId: Int): Result<Product>
}
