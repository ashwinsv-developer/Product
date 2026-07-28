package com.product.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val userName : String,
    val name :String ,
    val mobileNumber: String,
    val email: String,
    val occupation: String ,
    val password: String,
    val confirmPassword: String,
)
