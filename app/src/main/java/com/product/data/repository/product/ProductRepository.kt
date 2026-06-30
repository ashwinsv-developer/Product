package com.product.data.repository.product

import com.product.data.model.Product
import com.product.di.ApiResult
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getProducts(): Flow<ApiResult<List<Product>>>

}