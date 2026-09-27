package com.product.domain.usecase.login

import com.product.domain.model.User
import com.product.domain.repository.login.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repo: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<User> {
        if (email.isBlank()) return Result.failure(IllegalArgumentException("Email required"))
        if (password.length < 6) return Result.failure(IllegalArgumentException("Password too short"))
        return repo.login(email, password)
    }
}