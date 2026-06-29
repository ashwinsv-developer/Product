package com.product.data.remote

import com.product.data.model.ProductResponse
import com.product.data.model.SingleProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {
    @GET("public/randomproducts")
    suspend fun getProducts(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 100
    ): ProductResponse

    @GET("public/randomproducts/{productId}")
    suspend fun getProductDetails(
        @Path("productId") productId: Int
    ): SingleProductResponse

    companion object {
        const val BASE_URL = "https://api.freeapi.app/api/v1/"
    }
}
