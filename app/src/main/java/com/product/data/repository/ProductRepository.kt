package com.product.data.repository

import com.product.data.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(): Flow<Result<List<Product>>>
    fun getProductDetails(productId: Int): Flow<Result<Product>>
}
