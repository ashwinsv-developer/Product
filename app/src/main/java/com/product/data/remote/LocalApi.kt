package com.product.data.remote

import retrofit2.http.GET

interface LocalApi {

    @GET()
    suspend fun login ()

    companion object {
        const val BASE_URL = "http://127.0.0.1:8080/"
    }
}