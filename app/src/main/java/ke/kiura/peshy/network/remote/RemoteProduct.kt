package ke.kiura.peshy.network.remote

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RemoteProduct(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("price")
    val price: Double,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("discountPercentage")
    val discountPercentage: Double
) : Serializable