package com.product.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.product.data.SessionManager
import com.product.data.model.Product
import com.product.data.repository.productDetail.ProductDetailRepository
import com.product.di.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val sessionManager: SessionManager,
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
        MutableStateFlow<ApiResult<Product>>(
            ApiResult.Loading
        )

    val uiState: StateFlow<ApiResult<Product>> =
        _uiState.asStateFlow()

    init {
        fetchProductDetails()
    }

    fun fetchProductDetails() {

        viewModelScope.launch {

            repository
                .getProductDetails(productId)
                .collect { result ->

                    _uiState.value = result
                }
        }
    }
}