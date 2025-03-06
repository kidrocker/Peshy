package ke.kiura.peshy.network.remote

data class RemoteProducts(
    val products: List<RemoteProduct>,
    val total: Int,
    val skip: Int,
    val limit: Int
)