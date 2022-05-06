package lostankit7.droid.stockmarket.domain.repository

import kotlinx.coroutines.flow.Flow
import lostankit7.droid.stockmarket.domain.model.CompanyListing
import lostankit7.droid.stockmarket.util.Resource

interface StockRepository {
    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>
}