package com.product.domain.model

/**
 * Standardized domain-level exceptions to abstract away raw HTTP/Network exceptions.
 */
sealed class AppException(message: String, cause: Throwable? = null) : Exception(message, cause) {
    class NetworkException(message: String = "No internet connection", cause: Throwable? = null) : AppException(message, cause)
    class ServerException(message: String = "A server error occurred", cause: Throwable? = null) : AppException(message, cause)
    class UnauthorizedException(message: String = "Unauthorized access", cause: Throwable? = null) : AppException(message, cause)
    class ValidationException(message: String, cause: Throwable? = null) : AppException(message, cause)
    class UnknownException(message: String = "An unexpected error occurred", cause: Throwable? = null) : AppException(message, cause)
}
