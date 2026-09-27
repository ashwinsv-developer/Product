package com.product.ui.detail

import com.product.domain.model.Product

/**
 * UI State for the Product Detail screen.
 */
sealed interface ProductDetailUiState {
    data object Loading : ProductDetailUiState
    data class Success(val product: Product) : ProductDetailUiState
    data class Error(val message: String) : ProductDetailUiState
}
