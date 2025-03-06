package ke.kiura.peshy.network

import ke.kiura.peshy.network.remote.RemoteProducts
import ke.kiura.peshy.network.remote.RemoteProduct
import retrofit2.Response


interface NetworkDataSource {
    suspend fun getProducts(): Response<RemoteProducts>

    suspend fun getProductById(id: Int): Response<RemoteProduct>

}