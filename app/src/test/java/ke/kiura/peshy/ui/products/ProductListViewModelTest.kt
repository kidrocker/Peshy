package ke.kiura.peshy.ui.products

import io.mockk.mockk
import ke.kiura.peshy.data.model.Product
import ke.kiura.peshy.data.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
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

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = ProductListViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() = runTest {

    }

    @Test
    fun `successfully loading products updates state to Success`() = runTest {

    }

    @Test
    fun `network error updates state to Error`() = runTest {

    }

    @Test
    fun `search query updates products list`() = runTest {

    }

    @Test
    fun `empty search query returns all products`() = runTest {

    }

    @Test
    fun `search error updates state to Error`() = runTest {
    }

    @Test
    fun `refresh products success maintains current product list`() = runTest {

    }

    @Test
    fun `refresh products failure shows error state`() = runTest {

    }
}