package com.product.data.repository.productDetail

import com.product.data.mapper.toDomain
import com.product.data.remote.ProductApi
import com.product.domain.model.Product
import com.product.domain.repository.ProductDetailRepository
import javax.inject.Inject

/**
 * Implementation of ProductDetailRepository that communicates with the API.
 */
class ProductDetailRepositoryImpl @Inject constructor(
    private val api: ProductApi
) : ProductDetailRepository {

    override suspend fun getProductDetails(productId: Int): Result<Product> = runCatching {
        val response = api.getProductDetails(productId)
        if (response.success) {
            response.data.toDomain()
        } else {
            throw Exception(response.message)
        }
    }
}
