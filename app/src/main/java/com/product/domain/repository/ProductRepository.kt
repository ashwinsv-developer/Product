package com.product.domain.repository

import com.product.domain.model.Product

/**
 * Domain-level repository interface for Product operations.
 */
interface ProductRepository {
    suspend fun getProducts(): Result<List<Product>>
}
