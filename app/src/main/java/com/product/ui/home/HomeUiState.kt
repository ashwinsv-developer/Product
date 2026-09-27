package com.product.ui.home

import com.product.domain.model.Product

/**
 * UI State for the Home screen.
 */
sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val products: List<Product>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}
