package com.product.di

import com.product.data.repository.login.LoginRepositoryImpl
import com.product.data.repository.product.ProductRepositoryImpl
import com.product.data.repository.productDetail.ProductDetailRepositoryImpl
import com.product.domain.repository.ProductRepository
import com.product.domain.repository.ProductDetailRepository
import com.product.domain.repository.login.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        impl: ProductRepositoryImpl
    ): ProductRepository

    @Binds
    @Singleton
    abstract fun bindProductDetailRepository(
        impl: ProductDetailRepositoryImpl
    ): ProductDetailRepository

    @Binds
    @Singleton
    abstract fun bindLoginRepository(
        impl: LoginRepositoryImpl
    ): LoginRepository
}
