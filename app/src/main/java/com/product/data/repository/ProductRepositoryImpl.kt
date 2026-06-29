package com.product.data.repository

import com.product.data.model.Product
import com.product.data.remote.ProductApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi
) : ProductRepository {
    override fun getProducts(): Flow<Result<List<Product>>> = flow {
        try {
            val response = api.getProducts()
            if (response.success) {
                val products = response.data.data.map { it.fixUrls() }
                emit(Result.success(products))
            } else {
                emit(Result.failure(Exception(response.message)))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun getProductDetails(productId: Int): Flow<Result<Product>> = flow {
        try {
            val response = api.getProductDetails(productId)
            if (response.success) {
                emit(Result.success(response.data.fixUrls()))
            } else {
                emit(Result.failure(Exception(response.message)))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    private fun Product.fixUrls(): Product {
        return this.copy(
            thumbnail = thumbnail.fixUrl(),
            images = images.map { it.fixUrl() }
        )
    }

    private fun String.fixUrl(): String {
        return this.replace("product-images", "products/images")
    }
}
