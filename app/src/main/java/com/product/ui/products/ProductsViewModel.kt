package com.product.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.product.domain.usecase.product.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductsUiState>(ProductsUiState.Loading)
    val uiState: StateFlow<ProductsUiState> = _uiState.asStateFlow()

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _uiState.value = ProductsUiState.Loading
            getProductsUseCase()
                .onSuccess { products ->
                    _uiState.value = ProductsUiState.Success(products)
                }
                .onFailure { error ->
                    _uiState.value = ProductsUiState.Error(error.message ?: "Unknown Error")
                }
        }
    }
}
