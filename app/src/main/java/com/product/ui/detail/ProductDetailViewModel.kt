package com.product.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.product.data.model.Product
import com.product.data.repository.productDetail.ProductDetailRepository
import com.product.di.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface ProductDetailUiState {
    object Loading : ProductDetailUiState
    data class Success(val product: Product) : ProductDetailUiState
    data class Error(val message: String) : ProductDetailUiState
}

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: ProductDetailRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val productId: Int =
        checkNotNull(
            savedStateHandle["productId"]
        ) {
            "productId is required"
        }

    private val _uiState =
        MutableStateFlow<ProductDetailUiState>(
            ProductDetailUiState.Loading
        )

    val uiState: StateFlow<ProductDetailUiState> =
        _uiState.asStateFlow()

    init {
        fetchProductDetails()
    }

    fun fetchProductDetails() {

        viewModelScope.launch {

            repository
                .getProductDetails(productId)
                .collect { result ->

                    when (result) {

                        is ApiResult.Loading -> {
                            _uiState.value =
                                ProductDetailUiState.Loading
                        }

                        is ApiResult.Success -> {
                            _uiState.value =
                                ProductDetailUiState.Success(
                                    result.data
                                )
                        }

                        is ApiResult.Error -> {
                            _uiState.value =
                                ProductDetailUiState.Error(
                                    result.exception.message
                                        ?: "Unknown error"
                                )
                        }
                    }
                }
        }
    }
}