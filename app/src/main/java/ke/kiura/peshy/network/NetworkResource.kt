package ke.kiura.peshy.network

sealed class NetworkResource<out T> {
    data class Success<out T>(val data: T) : NetworkResource<T>()
    data class Failure(val error:String) : NetworkResource<Nothing>()
}