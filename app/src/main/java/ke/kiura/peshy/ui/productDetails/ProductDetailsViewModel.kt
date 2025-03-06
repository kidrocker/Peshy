package ke.kiura.peshy.ui.productDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import ke.kiura.peshy.data.Resource
import ke.kiura.peshy.data.model.Product
import ke.kiura.peshy.data.repository.ProductRepository
import ke.kiura.peshy.ui.navigation.ProductEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: ProductRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val product: ProductEvent = checkNotNull(savedStateHandle.toRoute<ProductEvent>())

    private val _uiState = MutableStateFlow<ProductDetailsUiState>(ProductDetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadProduct()
    }

    private fun loadProduct() {
        viewModelScope.launch {

            repository.getProductById(product.id).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let { _uiState.value = ProductDetailsUiState.Success(it) }
                    }

                    is Resource.Failure -> {
                        _uiState.value = ProductDetailsUiState.Error(response.error)
                    }

                    is Resource.Loading -> {
                        _uiState.value = ProductDetailsUiState.Loading
                    }

                }

            }

        }
    }
}

sealed class ProductDetailsUiState {
    data object Loading : ProductDetailsUiState()
    data class Success(val product: Product) : ProductDetailsUiState()
    data class Error(val message: String) : ProductDetailsUiState()
}