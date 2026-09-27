package com.product.data.repository.login

import com.product.data.mapper.toDomain
import com.product.data.model.login.LoginRequest
import com.product.data.remote.LocalApi

import com.product.domain.model.User
import com.product.domain.repository.login.LoginRepository
import javax.inject.Inject

import com.product.data.remote.runCatchingDomain

class LoginRepositoryImpl @Inject constructor(
    private val api: LocalApi
) : LoginRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Result<User> = runCatchingDomain {
        api.login(
            LoginRequest(email = email, password = password)
        ).toDomain()
    }
}