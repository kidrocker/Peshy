package ke.kiura.peshy.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.io.IOException

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val actNw =
        connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || actNw.hasTransport(
            NetworkCapabilities.TRANSPORT_CELLULAR
        ) || actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

        else -> false
    }
}

object NoInternet : IOException("No internet connection") {
    private fun readResolve(): Any = NoInternet
}