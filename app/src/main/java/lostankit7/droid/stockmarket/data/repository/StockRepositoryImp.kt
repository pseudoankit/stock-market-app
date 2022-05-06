package lostankit7.droid.stockmarket.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import lostankit7.droid.stockmarket.R
import lostankit7.droid.stockmarket.data.local.LocalDataBase
import lostankit7.droid.stockmarket.data.mapper.toCompanyListing
import lostankit7.droid.stockmarket.data.remote.StockApi
import lostankit7.droid.stockmarket.domain.model.CompanyListing
import lostankit7.droid.stockmarket.domain.repository.StockRepository
import lostankit7.droid.stockmarket.util.Resource
import lostankit7.droid.stockmarket.util.StringHandler
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImp @Inject constructor(
    val api: StockApi,
    val db: LocalDataBase,
) : StockRepository {

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String,
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))

            val localListings = db.dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListings.map { it.toCompanyListing() }
            ))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldLoadFromCache = !isDbEmpty && !fetchFromRemote

            if(shouldLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try {
                val response = api.getListings()
                //parse csv file to show in ui
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(StringHandler.ResourceString(R.string.error_loading_data)))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(StringHandler.ResourceString(R.string.error_loading_data)))
            }

        }
    }
}