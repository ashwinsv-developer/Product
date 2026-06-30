package com.product.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.product.data.SessionManager
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
class HomeViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState =
        MutableStateFlow<ApiResult<List<Product>>>(
            ApiResult.Loading
        )

    val uiState:
            StateFlow<ApiResult<List<Product>>> =
        _uiState.asStateFlow()

    private var allProducts: List<Product> =
        emptyList()

    var categories by mutableStateOf<List<String>>(
        emptyList()
    )
        private set

    var selectedCategory by mutableStateOf("All")
        private set

    init {
        fetchProducts()
    }

    fun fetchProducts() {

        viewModelScope.launch {

            productRepository
                .getProducts()
                .collect { result ->

                    when (result) {

                        is ApiResult.Loading -> {
                            _uiState.value =
                                ApiResult.Loading
                        }

                        is ApiResult.Success -> {

                            allProducts =
                                result.data

                            categories =
                                listOf("All") +
                                        result.data
                                            .map {
                                                it.category
                                            }
                                            .distinct()
                                            .sorted()

                            filterProducts()
                        }

                        is ApiResult.Error -> {

                            _uiState.value =
                                ApiResult.Error(
                                    result.exception
                                )
                        }
                    }
                }
        }
    }

    private fun filterProducts() {

        val filtered =
            if (selectedCategory == "All") {
                allProducts
            } else {
                allProducts.filter {
                    it.category == selectedCategory
                }
            }

        _uiState.value =
            ApiResult.Success(filtered)
    }

    fun selectCategory(
        category: String
    ) {
        selectedCategory = category
        filterProducts()
    }

    fun getUserEmail(): String {
        return sessionManager.getEmail() ?: "Unknown"
    }

    fun logout() {
        sessionManager.clearSession()
    }
}

