package com.product.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RetryInterceptor(
    private val maxRetries: Int = 3
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()
        var response: Response? = null
        var retryCount = 0
        var exception: Exception? = null

        while (retryCount < maxRetries) {
            try {
                response = chain.proceed(request)

                if (response.isSuccessful) {
                    return response
                }

            } catch (e: Exception) {
                exception = e
            }

            retryCount++

            // Exponential backoff
            Thread.sleep((1000L * retryCount))
        }

        response?.let { return it }

        throw exception ?:IOException("Unknown network error")
    }
}