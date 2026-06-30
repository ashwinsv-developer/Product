package com.product.data.repository.productDetail

import com.product.data.model.Product
import com.product.di.ApiResult
import kotlinx.coroutines.flow.Flow

interface ProductDetailRepository {

    fun getProductDetails(
        productId: Int
    ): Flow<ApiResult<Product>>
}