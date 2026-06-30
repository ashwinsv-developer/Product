package com.product.data.repository.product

import com.product.data.model.Product
import com.product.data.remote.ProductApi
import com.product.di.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi
) : ProductRepository {

    override fun getProducts(): Flow<ApiResult<List<Product>>> =
        flow {

            emit(ApiResult.Loading)

            try {

                val response = api.getProducts()

                if (response.success) {

                    val products =
                        response.data.data.map {
                            it.fixUrls()
                        }

                    emit(
                        ApiResult.Success(products)
                    )

                } else {

                    emit(
                        ApiResult.Error(
                            Exception(response.message)
                        )
                    )
                }

            } catch (e: Exception) {

                emit(
                    ApiResult.Error(e)
                )
            }
        }


    private fun Product.fixUrls(): Product {
        return copy(
            thumbnail = thumbnail.fixUrl(),
            images = images.map {
                it.fixUrl()
            }
        )
    }

    private fun String.fixUrl(): String {
        return replace(
            "product-images",
            "products/images"
        )
    }
}