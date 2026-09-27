package com.product.domain.repository

import com.product.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun insertUser(user: UserEntity)
    suspend fun getUserByUsername(userName: String): UserEntity?
    fun getAllUsers(): Flow<List<UserEntity>>
}