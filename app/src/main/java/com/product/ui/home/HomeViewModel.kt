package com.product.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.product.data.SessionManager
import com.product.data.model.Product
import com.product.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val productRepository: ProductRepository
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
            productRepository.getProducts().collect { result ->
                result.onSuccess { products ->
                    allProducts = products
                    categories = listOf("All") + products.map { it.category }.distinct().sorted()
                    filterProducts()
                }.onFailure { error ->
                    _uiState.value = HomeUiState.Error(error.message ?: "Unknown Error")
                }
            }
        }
    }

    fun selectCategory(category: String) {
        selectedCategory = category
        filterProducts()
    }

    private fun filterProducts() {
        val filtered = if (selectedCategory == "All") {
            allProducts
        } else {
            allProducts.filter { it.category == selectedCategory }
        }
        _uiState.value = HomeUiState.Success(filtered)
    }

    fun getUserEmail(): String {
        return sessionManager.getEmail() ?: "Unknown"
    }

    fun logout() {
        sessionManager.clearSession()
    }
}

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val products: List<Product>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}
