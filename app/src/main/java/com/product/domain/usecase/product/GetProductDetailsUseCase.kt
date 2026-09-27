package com.product.domain.usecase.product

import com.product.domain.model.Product
import com.product.domain.repository.ProductDetailRepository
import javax.inject.Inject

/**
 * Use case to retrieve product details by ID.
 */
class GetProductDetailsUseCase @Inject constructor(
    private val repository: ProductDetailRepository
) {
    suspend operator fun invoke(productId: Int): Result<Product> = 
        repository.getProductDetails(productId)
}
