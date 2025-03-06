package ke.kiura.peshy.data.repository

import android.content.Context
import ke.kiura.peshy.data.Resource
import ke.kiura.peshy.data.model.Product
import ke.kiura.peshy.db.dao.ProductDao
import ke.kiura.peshy.network.NetworkDataSource
import ke.kiura.peshy.network.NetworkResource
import ke.kiura.peshy.network.retrofit.BaseApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    context: Context,
    private val networkDataSource: NetworkDataSource,
    private val dao: ProductDao
) : ProductRepository, BaseApiResponse(context) {
    override fun getAllProducts(): Flow<Resource<List<Product>>> = flow {

        dao.getAllProducts().collect { entities ->
            emit(Resource.Success(entities.map { it.toProduct() }))
        }
    }

    override fun searchProducts(query: String): Flow<Resource<List<Product>>> = flow {
        emit(Resource.Loading)

        dao.searchProducts(query).map { entities ->
            emit(Resource.Success(entities.map { it.toProduct() }))
        }
    }

    override fun refreshProducts(): Flow<Resource<String>> = flow {
        emit(Resource.Loading)

        val response = handleApiCall { networkDataSource.getProducts() }
        when (response) {
            is NetworkResource.Success -> dao.insertProducts(response.data.products.map { it.toProductEntity() })
            is NetworkResource.Failure -> emit(Resource.Failure(response.error))
        }
    }

    override fun getProductById(id: Int): Flow<Resource<Product?>> = flow {
        emit(Resource.Loading)
        dao.getProductById(id).collect {
            emit(Resource.Success(it?.toProduct()))
        }
    }
}
