package com.product.data.remote

import com.product.data.model.ProductResponse
import com.product.data.model.login.LoginRequest
import com.product.data.model.login.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LocalApi {
    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}