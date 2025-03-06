package ke.kiura.peshy.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ke.kiura.peshy.datastore.KeyValueStore
import ke.kiura.peshy.network.NetworkDataSource
import ke.kiura.peshy.network.retrofit.RetrofitNetwork
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Provides
    @Singleton
    fun okHttpCallFactory(): Call.Factory = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    setLevel(HttpLoggingInterceptor.Level.BODY) // should change in production
                },
        )
        .build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context): Cache {
        val httpCacheDirectory = File(context.cacheDir.absolutePath, "HttpCache")
        return Cache(httpCacheDirectory, (10 * 1024 * 1024L))
    }

    @Provides
    @Singleton
    fun provideNetworkDataSource(
        gson: Gson,
        okhttpCallFactory: Call.Factory,
        loggingInterceptor: HttpLoggingInterceptor,
        cache: Cache,
        keyValueStore: KeyValueStore
    ): NetworkDataSource = RetrofitNetwork(
        gson = gson,
        okhttpCallFactory = okhttpCallFactory,
        loggingInterceptor = loggingInterceptor,
        cache = cache,
        keyValueStore = keyValueStore
    )
}