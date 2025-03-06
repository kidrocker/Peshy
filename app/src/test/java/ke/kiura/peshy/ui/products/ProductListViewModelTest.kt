package ke.kiura.peshy.ui.products

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import ke.kiura.peshy.data.Resource
import ke.kiura.peshy.data.model.Product
import ke.kiura.peshy.data.repository.ProductRepository
import ke.kiura.peshy.ui.products.ProductListViewModel.ProductListUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProductListViewModelTest {

    private lateinit var viewModel: ProductListViewModel
    private lateinit var repository: ProductRepository
    private val testDispatcher = StandardTestDispatcher()

    private val sampleProducts = listOf(
        Product(
            id = 1,
            title = "iPhone 9",
            description = "An apple mobile which is nothing like apple",
            price = 549.0,
            thumbnail = "https://example.com/iphone9.jpg"
        ),
        Product(
            id = 2,
            title = "iPhone X",
            description = "SIM-Free, Model A19211 6.5-inch Super Retina HD display",
            price = 899.0,
            thumbnail = "https://example.com/iphonex.jpg"
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = ProductListViewModel(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() = runTest {
        viewModel.uiState.test {
            assertEquals(ProductListUiState.Loading, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `successfully loading products updates state to Success`() = runTest {
        // Given
        coEvery { repository.getAllProducts() } returns flowOf(Resource.Success(sampleProducts))
        coEvery { repository.refreshProducts() } returns flowOf(Resource.Success(""))

        // When & Then
        viewModel.uiState.test {
            assertEquals(ProductListUiState.Loading, awaitItem())
            assertEquals(ProductListUiState.Success(sampleProducts), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `network error updates state to Error`() = runTest {
        // Given
        val errorMessage = "Network error"
        coEvery { repository.getAllProducts() } returns flowOf(Resource.Failure(errorMessage))
        coEvery { repository.refreshProducts() } returns flowOf(Resource.Failure(errorMessage))

        // When & Then
        viewModel.uiState.test {
            assertEquals(ProductListUiState.Loading, awaitItem())
            assertEquals(ProductListUiState.Error(errorMessage), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `search query updates products list`() = runTest {
        // Given
        val searchQuery = "iPhone"
        val filteredProducts = listOf(sampleProducts[0])
        coEvery { repository.searchProducts(searchQuery) } returns flowOf(
            Resource.Success(
                filteredProducts
            )
        )

        // When
        viewModel.onSearchQueryChange(searchQuery)

        // Then
        viewModel.uiState.test {
            assertEquals(ProductListUiState.Loading, awaitItem())
            assertEquals(ProductListUiState.Success(filteredProducts), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `empty search query returns all products`() = runTest {
        // Given
        coEvery { repository.getAllProducts() } returns flowOf(Resource.Success(sampleProducts))
        coEvery { repository.refreshProducts() } returns flowOf(Resource.Success(""))

        // When
        viewModel.onSearchQueryChange("i")

        // Then
        viewModel.uiState.test {
            assertEquals(ProductListUiState.Loading, awaitItem())
            assertEquals(ProductListUiState.Success(sampleProducts), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `search error updates state to Error`() = runTest {
        // Given
        val searchQuery = "iPhone"
        val errorMessage = "Search failed"
        coEvery { repository.searchProducts(searchQuery) } returns flowOf(
            Resource.Failure(
                errorMessage
            )
        )

        // When
        viewModel.onSearchQueryChange(searchQuery)

        // Then
        viewModel.uiState.test {
            assertEquals(ProductListUiState.Loading, awaitItem())
            assertEquals(ProductListUiState.Error(errorMessage), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `refresh products success maintains current product list`() = runTest {
        // Given
        coEvery { repository.getAllProducts() } returns flowOf(Resource.Success(sampleProducts))
        coEvery { repository.refreshProducts() } returns flowOf(Resource.Success(""))

        // When & Then
        viewModel.uiState.test {
            assertEquals(ProductListUiState.Loading, awaitItem())
            assertEquals(ProductListUiState.Success(sampleProducts), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `refresh products failure shows error state`() = runTest {
        // Given
        val errorMessage = "Refresh failed"
        coEvery { repository.getAllProducts() } returns flowOf(Resource.Success(sampleProducts))
        coEvery { repository.refreshProducts() } returns flowOf(Resource.Failure(errorMessage))

        // When & Then
        viewModel.uiState.test {
            assertEquals(ProductListUiState.Loading, awaitItem())
            assertEquals(ProductListUiState.Error(errorMessage), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}