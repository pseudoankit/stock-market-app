package lostankit7.droid.stockmarket.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import lostankit7.droid.stockmarket.data.local.dao.StockDao
import lostankit7.droid.stockmarket.data.local.enitity.CompanyListingEntity

@Database(
    entities = [CompanyListingEntity::class],
    version = 1
)
abstract class LocalDataBase : RoomDatabase() {
    abstract val stockDao: StockDao
}