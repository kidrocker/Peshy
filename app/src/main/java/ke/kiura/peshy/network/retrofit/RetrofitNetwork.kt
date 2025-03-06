package ke.kiura.peshy.network.retrofit

import com.google.gson.Gson
import ke.kiura.peshy.datastore.KeyValueStore
import ke.kiura.peshy.network.NetworkDataSource
import ke.kiura.peshy.network.remote.RemoteProduct
import ke.kiura.peshy.network.remote.RemoteProducts
import okhttp3.Cache
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitNetwork @Inject constructor(
    gson: Gson,
    okhttpCallFactory: Call.Factory,
    loggingInterceptor: HttpLoggingInterceptor,
    cache: Cache,
    keyValueStore: KeyValueStore
) : NetworkDataSource {

    private val networkApi =
        Retrofit.Builder().baseUrl("https://dummyjson.com/") // should be stored in config
            .callFactory(okhttpCallFactory).client(
                OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request =
                            chain.request().newBuilder()
                                //.addHeader("Bearer", token) Add token here
                                .addHeader("content-type", "application/json; charset=utf-8")
                                .build()
                        return@addInterceptor chain.proceed(request)
                    }
                    .addInterceptor(loggingInterceptor)
                    .cache(cache)
                    .build()
            ).addConverterFactory(
                GsonConverterFactory.create(gson),
            ).build()
            .create(RetrofitApi::class.java)

    override suspend fun getProducts(): Response<RemoteProducts> = networkApi.getProducts()

    override suspend fun getProductById(id: Int): Response<RemoteProduct> = networkApi.getProductById(id)
}
