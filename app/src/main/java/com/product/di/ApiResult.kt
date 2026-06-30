package com.product.di

import com.product.data.model.Product




sealed class ApiResult<out R> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val exception: Exception) : ApiResult<Nothing>()
    data object Loading : ApiResult<Nothing>()
}