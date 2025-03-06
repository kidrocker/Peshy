package ke.kiura.peshy.network.retrofit

import ke.kiura.peshy.network.remote.RemoteProduct
import ke.kiura.peshy.network.remote.RemoteProducts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitApi {
    @GET("products")
    suspend fun getProducts(): Response<RemoteProducts>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Response<RemoteProduct>
}