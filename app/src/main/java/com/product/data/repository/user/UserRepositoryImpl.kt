package com.product.data.repository.user

import com.product.data.local.dao.UserDao
import com.product.data.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
) : UserRepository {
    override suspend fun insertUser(user: UserEntity) {
        userDao.insertUser(user)
    }

    override suspend fun getUserByUsername(userName: String): UserEntity? {
        return userDao.getUserByUsername(userName)
    }

    override fun getAllUsers(): Flow<List<UserEntity>> {
        return userDao.getAllUsers()
    }
}
