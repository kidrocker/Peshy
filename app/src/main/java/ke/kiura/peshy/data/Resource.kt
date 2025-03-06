package ke.kiura.peshy.data

sealed class Resource<out T> {
    data object Loading: Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val error:String) : Resource<Nothing>()
}