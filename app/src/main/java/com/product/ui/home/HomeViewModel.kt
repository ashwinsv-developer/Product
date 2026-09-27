package com.product.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.product.domain.model.Product
import com.product.domain.usecase.product.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var allProducts: List<Product> = emptyList()

    var categories by mutableStateOf<List<String>>(emptyList())
        private set

    var selectedCategory by mutableStateOf("All")
        private set

    init {
        fetchProducts()
    }

    fun fetchProducts() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading
            getProductsUseCase()
                .onSuccess { products ->
                    allProducts = products
                    categories = listOf("All") + products.map { it.category }.distinct().sorted()
                    filterProducts()
                }
                .onFailure { error ->
                    _uiState.value = HomeUiState.Error(error.message ?: "Unknown Error")
                }
        }
    }

    private fun filterProducts() {
        val filtered = if (selectedCategory == "All") {
            allProducts
        } else {
            allProducts.filter { it.category == selectedCategory }
        }
        _uiState.value = HomeUiState.Success(filtered)
    }

    fun selectCategory(category: String) {
        selectedCategory = category
        filterProducts()
    }
}
