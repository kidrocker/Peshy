package ke.kiura.peshy.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey

import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "ke.kiura.peshy.datastore.v1")

class AndroidKeyValueStore(
    private val context: Context
) : KeyValueStore {

    override suspend fun <T> store(key: Key<T>, value: T?) {
        val prefKey = key.asPreferencesKey()
        context.dataStore.edit { keyValueStore ->
            if (value == null) {
                keyValueStore.remove(prefKey)
            } else {
                keyValueStore[prefKey] = value
            }
        }
    }

    override fun <T> load(key: Key<T>): Flow<T?> {
        val prefKey: Preferences.Key<T> = key.asPreferencesKey()
        return context.dataStore.data
            .map { keyValueStore ->
                keyValueStore[prefKey]
            }.distinctUntilChanged()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> Key<T>.asPreferencesKey(): Preferences.Key<T> {
        return when (this) {
            is Key.Boolean -> booleanPreferencesKey(value)
            is Key.String -> stringPreferencesKey(value)
            is Key.Int -> intPreferencesKey(value)
        } as Preferences.Key<T>
    }
}