package ke.kiura.peshy.db

import ke.kiura.peshy.db.dao.ProductDao
import ke.kiura.peshy.db.dao.ProductEntity
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ProductEntity::class],
    version = 1
)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}