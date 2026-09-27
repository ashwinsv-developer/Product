package com.product.domain.usecase.product

import com.product.domain.model.Product
import com.product.domain.repository.ProductRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Unit test for GetProductsUseCase.
 */
class GetProductsUseCaseTest {

    private val repository: ProductRepository = mockk()
    private val getProductsUseCase = GetProductsUseCase(repository)

    @Test
    fun `invoke should return success result when repository succeeds`() = runTest {
        // Given
        val mockProducts = listOf(
            Product(
                id = 1,
                title = "Test Product",
                description = "Description",
                price = 100,
                discountPercentage = 10.0,
                rating = 4.5,
                stock = 50,
                brand = "Brand",
                category = "Category",
                thumbnail = "url",
                images = emptyList()
            )
        )
        coEvery { repository.getProducts() } returns Result.success(mockProducts)

        // When
        val result = getProductsUseCase()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(mockProducts, result.getOrNull())
    }

    @Test
    fun `invoke should return failure result when repository fails`() = runTest {
        // Given
        val exception = Exception("Network Error")
        coEvery { repository.getProducts() } returns Result.failure(exception)

        // When
        val result = getProductsUseCase()

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
