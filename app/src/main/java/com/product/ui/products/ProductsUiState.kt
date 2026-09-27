package com.product.ui.products

import com.product.domain.model.Product

/**
 * UI State for the Products list screen.
 */
sealed interface ProductsUiState {
    data object Loading : ProductsUiState
    data class Success(val products: List<Product>) : ProductsUiState
    data class Error(val message: String) : ProductsUiState
}
