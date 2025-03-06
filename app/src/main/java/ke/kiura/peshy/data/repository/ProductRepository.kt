package ke.kiura.peshy.data.repository

import ke.kiura.peshy.data.Resource
import ke.kiura.peshy.data.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    fun getAllProducts(): Flow<Resource<List<Product>>>

    fun searchProducts(query: String): Flow<Resource<List<Product>>>

    fun refreshProducts():Flow<Resource<String>>

    fun getProductById(id: Int): Flow<Resource<Product?>>
}