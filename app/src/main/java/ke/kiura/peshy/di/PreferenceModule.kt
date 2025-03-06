package ke.kiura.peshy.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ke.kiura.peshy.datastore.AndroidKeyValueStore
import ke.kiura.peshy.datastore.KeyValueStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    @Provides
    @Singleton
    fun bindKeyValueStore(
        @ApplicationContext app: Context
    ): KeyValueStore {
        return AndroidKeyValueStore(app)
    }
}