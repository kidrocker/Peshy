package ke.kiura.peshy.ui.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ke.kiura.peshy.data.Resource
import ke.kiura.peshy.data.model.Product
import ke.kiura.peshy.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    val searchQuery = MutableStateFlow("")

    init {
        viewModelScope.launch {
            repository.getAllProducts().collect { resp ->
                handleResponse(resp)
            }
        }
    }

    private val _uiState: MutableStateFlow<ProductListUiState> =
        MutableStateFlow(ProductListUiState.Loading)

    val uiState = _uiState.onStart {
        refreshProducts()
    }.stateIn(
        viewModelScope,
        initialValue = ProductListUiState.Loading,
        started = WhileSubscribed(5000)
    )

    fun onSearchQueryChange(query: String) {
        searchQuery.update { query }
        viewModelScope.launch {
            repository.searchProducts(query).collect { resp ->
                handleResponse(resp)
            }
        }
    }

    private fun handleResponse(resp: Resource<List<Product>>) {

        when (resp) {
            is Resource.Success -> {
                _uiState.update { ProductListUiState.Success(resp.data) }
            }

            is Resource.Failure -> {
                _uiState.update { ProductListUiState.Error(resp.error) }
            }

            is Resource.Loading -> {
                _uiState.update { ProductListUiState.Loading }
            }
        }
    }

    private fun refreshProducts() {
        viewModelScope.launch {
            repository.refreshProducts().collect { resp ->
                when (resp) {
                    is Resource.Failure -> _uiState.update { ProductListUiState.Error(resp.error) }
                    is Resource.Loading -> _uiState.update { ProductListUiState.Loading }
                    else -> Unit // we do not expect success to be triggered here
                }
            }
        }
    }

    sealed interface ProductListUiState {
        data class Success(val products: List<Product>) : ProductListUiState
        data object Loading : ProductListUiState
        data class Error(val message: String) : ProductListUiState
    }
}