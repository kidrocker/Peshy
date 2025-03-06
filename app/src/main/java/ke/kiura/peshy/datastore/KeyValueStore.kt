package ke.kiura.peshy.datastore

import kotlinx.coroutines.flow.Flow

interface KeyValueStore {

    suspend fun <T> store(key: Key<T>, value: T?)

    fun <T> load(key: Key<T>): Flow<T?>
}

sealed interface Key<T> {
    @JvmInline
    value class String(val value: kotlin.String) : Key<kotlin.String>

    @JvmInline
    value class Boolean(val value: kotlin.String) : Key<kotlin.Boolean>

    @JvmInline
    value class Int(val value: kotlin.String) : Key<kotlin.Int>
}
