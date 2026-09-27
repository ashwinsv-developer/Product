package com.product.data.remote

import com.product.domain.model.AppException
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection

/**
 * Global application-level data layer exception translator.
 * Maps raw underlying framework exceptions (IOException, HttpException) into clean domain AppException models.
 */
object ErrorHandler {
    fun toDomainException(throwable: Throwable): AppException {
        return when (throwable) {
            is IOException -> AppException.NetworkException(
                message = "No internet connection. Please try again.",
                cause = throwable
            )
            is HttpException -> {
                when (throwable.code()) {
                    HttpURLConnection.HTTP_UNAUTHORIZED -> AppException.UnauthorizedException(
                        message = "Session expired or invalid credentials.",
                        cause = throwable
                    )
                    HttpURLConnection.HTTP_INTERNAL_ERROR, 
                    HttpURLConnection.HTTP_BAD_GATEWAY, 
                    HttpURLConnection.HTTP_UNAVAILABLE -> AppException.ServerException(
                        message = "The server is currently unavailable. Please try again later.",
                        cause = throwable
                    )
                    else -> AppException.UnknownException(
                        message = "Server error occurred: ${throwable.message()}",
                        cause = throwable
                    )
                }
            }
            is AppException -> throwable
            else -> AppException.UnknownException(
                message = throwable.localizedMessage ?: "An unexpected error occurred.",
                cause = throwable
            )
        }
    }
}

/**
 * Convenient custom extension function to catch exceptions and map them directly using ErrorHandler.
 */
inline fun <T> runCatchingDomain(block: () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (e: Throwable) {
        Result.failure(ErrorHandler.toDomainException(e))
    }
}
