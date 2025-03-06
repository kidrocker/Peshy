package ke.kiura.peshy.di

import android.content.Context
import dagger.Binds
import dagger.Module;
import dagger.Provides
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent;
import ke.kiura.peshy.data.repository.ProductRepository
import ke.kiura.peshy.data.repository.ProductRepositoryImpl
import ke.kiura.peshy.db.dao.ProductDao
import ke.kiura.peshy.network.NetworkDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideProductRepository(
        networkDataSource: NetworkDataSource,
        productDao: ProductDao,
        @ApplicationContext context: Context
    ): ProductRepository =
        ProductRepositoryImpl(
            networkDataSource = networkDataSource,
            dao = productDao,
            context = context
        )
}