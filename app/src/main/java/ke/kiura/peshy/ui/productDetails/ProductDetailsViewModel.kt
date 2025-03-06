package ke.kiura.peshy.ui.productDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ke.kiura.peshy.data.model.Product
import ke.kiura.peshy.data.repository.ProductRepository
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repository: ProductRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
//    private val productId: Int = checkNotNull(savedStateHandle["productId"])
//
//    private val _uiState = MutableStateFlow<ProductDetailsUiState>(ProductDetailsUiState.Loading)
//    val uiState = _uiState.asStateFlow()
//
//    init {
//        loadProduct()
//    }
//
//    private fun loadProduct() {
//        viewModelScope.launch {
//            _uiState.value = ProductDetailsUiState.Loading
//            try {
//                val product = repository.getProductById(productId)
//                if (product != null) {
//                    _uiState.value = ProductDetailsUiState.Success(product)
//                } else {
//                    _uiState.value = ProductDetailsUiState.Error("Product not found")
//                }
//            } catch (e: Exception) {
//                _uiState.value = ProductDetailsUiState.Error(e.message ?: "Unknown error")
//            }
//        }
//    }
}

sealed class ProductDetailsUiState {
    object Loading : ProductDetailsUiState()
    data class Success(val product: Product) : ProductDetailsUiState()
    data class Error(val message: String) : ProductDetailsUiState()
}