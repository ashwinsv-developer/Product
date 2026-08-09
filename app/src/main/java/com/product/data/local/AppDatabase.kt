package com.product.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.product.data.local.dao.MovieDao
import com.product.data.local.dao.UserDao
import com.product.data.local.entity.GenreEntity
import com.product.data.local.entity.MovieEntity
import com.product.data.local.entity.UserEntity

@Database(entities = [MovieEntity::class, GenreEntity::class, UserEntity::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun userDao(): UserDao
}
