package com.product.domain.usecase.product

import com.product.domain.model.Product
import com.product.domain.repository.ProductRepository
import javax.inject.Inject

/**
 * Use case to retrieve the list of products.
 */
class GetProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(): Result<List<Product>> = repository.getProducts()
}
