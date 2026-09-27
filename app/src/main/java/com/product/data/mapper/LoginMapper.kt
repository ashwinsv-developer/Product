package com.product.data.mapper

import com.product.data.model.login.LoginResponse
import com.product.domain.model.User

fun LoginResponse.toDomain() : User {
    return User(
        accessToken = this.accessToken,
        userName = this.userName,
        name = this.name
    )
}