package com.product.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.product.data.model.Product
import com.product.data.repository.product.ProductRepository
import com.product.di.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ApiResult<List<Product>>>(ApiResult.Loading)
    val uiState: StateFlow<ApiResult<List<Product>>> = _uiState.asStateFlow()

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            productRepository.getProducts().collect { result ->
                _uiState.value = result
            }
        }
    }
}
