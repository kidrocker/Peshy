package ke.kiura.peshy.data.repository

import ke.kiura.peshy.data.model.Product
import ke.kiura.peshy.db.dao.ProductEntity
import ke.kiura.peshy.network.remote.RemoteProduct

fun ProductEntity.toProduct(): Product {
    return Product(
        id = id,
        title = title,
        description = description,
        price = price,
        thumbnail = thumbnail
    )
}

fun RemoteProduct.toProductEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        title = title,
        description = description,
        price = price,
        thumbnail = thumbnail
    )
}