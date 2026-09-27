package com.product.ui.home

import com.product.domain.model.Product
import com.product.domain.usecase.product.GetProductsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val getProductsUseCase: GetProductsUseCase = mockk()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should fetch products and update uiState to Success`() = runTest {
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
                category = "Electronics",
                thumbnail = "url",
                images = emptyList()
            )
        )
        coEvery { getProductsUseCase() } returns Result.success(mockProducts)

        // When
        val viewModel = HomeViewModel(getProductsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(viewModel.uiState.value is HomeUiState.Success)
        assertEquals(mockProducts, (viewModel.uiState.value as HomeUiState.Success).products)
        assertEquals(listOf("All", "Electronics"), viewModel.categories)
    }

    @Test
    fun `init should fetch products and update uiState to Error on failure`() = runTest {
        // Given
        val errorMessage = "Network Failure"
        coEvery { getProductsUseCase() } returns Result.failure(Exception(errorMessage))

        // When
        val viewModel = HomeViewModel(getProductsUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        assertTrue(viewModel.uiState.value is HomeUiState.Error)
        assertEquals(errorMessage, (viewModel.uiState.value as HomeUiState.Error).message)
    }
}
