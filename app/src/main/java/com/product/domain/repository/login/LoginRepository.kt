package com.product.domain.repository.login


import com.product.domain.model.User

interface LoginRepository {
    suspend fun login(email: String, password: String): Result<User>
}