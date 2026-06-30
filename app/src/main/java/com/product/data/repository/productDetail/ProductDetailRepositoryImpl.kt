package com.product.data.repository.productDetail

import com.product.data.model.Product
import com.product.data.remote.ProductApi
import com.product.di.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductDetailRepositoryImpl @Inject constructor(
    private val api: ProductApi
) : ProductDetailRepository {


    override fun getProductDetails(
        productId: Int
    ): Flow<ApiResult<Product>> =
        flow {

            emit(ApiResult.Loading)

            try {

                val response =
                    api.getProductDetails(productId)

                if (response.success) {

                    emit(
                        ApiResult.Success(
                            response.data
                        )
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


}