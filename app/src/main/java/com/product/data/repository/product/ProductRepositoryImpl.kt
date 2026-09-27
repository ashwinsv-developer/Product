package com.product.data.repository.product

import com.product.data.mapper.toDomain
import com.product.data.remote.ProductApi
import com.product.data.remote.runCatchingDomain
import com.product.domain.model.Product
import com.product.domain.repository.ProductRepository
import javax.inject.Inject

/**
 * Implementation of ProductRepository that communicates with the API.
 * Maps data layer models to domain layer models using an application-level common exception handler.
 */
class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi
) : ProductRepository {

    override suspend fun getProducts(): Result<List<Product>> = runCatchingDomain {
        val response = api.getProducts()
        if (response.success) {
            response.data.data.map { it.toDomain() }
        } else {
            throw Exception(response.message)
        }
    }
}
