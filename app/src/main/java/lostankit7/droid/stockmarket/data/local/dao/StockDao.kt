package lostankit7.droid.stockmarket.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import lostankit7.droid.stockmarket.data.local.enitity.CompanyListingEntity

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(
        companyListingEntities: List<CompanyListingEntity>,
    )

    @Query("DELETE FROM companylistingentity")
    suspend fun clearCompanyListings()

    @Query("""
        SELECT * FROM companylistingentity
        WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
            UPPER(:query) == symbol
    """)
    suspend fun searchCompanyListing(query: String): List<CompanyListingEntity>
}