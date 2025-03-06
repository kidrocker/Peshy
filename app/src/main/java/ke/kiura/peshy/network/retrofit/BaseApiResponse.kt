package ke.kiura.peshy.network.retrofit

import android.content.Context
import ke.kiura.peshy.network.NetworkResource
import ke.kiura.peshy.network.NoInternet
import ke.kiura.peshy.network.isInternetAvailable
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException

/**
 * Generic class to handle the response and all errors
 * The class allows for a silent fail by wrapping all call in a try catch block
 * Finally the class extracts and formats errors from network calls
 */
abstract class BaseApiResponse(
    private val context: Context
) {

    suspend fun <T> handleApiCall(apiCall: suspend () -> Response<T>): NetworkResource<T> {
        try {

            if (!isInternetAvailable(context)) {
                throw NoInternet
            }

            val response = apiCall()
            val body = response.body()
            body?.let {
                return NetworkResource.Success(body)
            }
            return error(response)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Throwable) {
            return error(e)
        }
    }

    private fun <T> error(error: Throwable): NetworkResource<T> {

        return when (error) {
            is ConnectException -> NetworkResource.Failure("Our service is experiencing connection issues at the moment. Please try again in a short while.")
            is HttpException -> NetworkResource.Failure("We are unable to process your request at the moment. Please try again after a while")
            is NoInternet -> NetworkResource.Failure("Internet connection is not available")
            else -> NetworkResource.Failure("Error occurred: ${error.message}")
        }
    }
}