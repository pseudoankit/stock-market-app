package lostankit7.droid.stockmarket.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import lostankit7.droid.stockmarket.R
import lostankit7.droid.stockmarket.data.csv.CSVParser
import lostankit7.droid.stockmarket.data.csv.IntradayInfoParser
import lostankit7.droid.stockmarket.data.local.LocalDataBase
import lostankit7.droid.stockmarket.data.mapper.toCompanyInfo
import lostankit7.droid.stockmarket.data.mapper.toCompanyListing
import lostankit7.droid.stockmarket.data.mapper.toCompanyListingEntity
import lostankit7.droid.stockmarket.data.remote.StockApi
import lostankit7.droid.stockmarket.domain.model.CompanyInfo
import lostankit7.droid.stockmarket.domain.model.CompanyListing
import lostankit7.droid.stockmarket.domain.model.IntradayInfo
import lostankit7.droid.stockmarket.domain.repository.StockRepository
import lostankit7.droid.stockmarket.util.Resource
import lostankit7.droid.stockmarket.util.StringHandler
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImp @Inject constructor(
    db: LocalDataBase,
    private val api: StockApi,
    private val companyListingParser: CSVParser<CompanyListing>,
    private val intradayInfoParser: IntradayInfoParser,
) : StockRepository {

    private val dao = db.stockDao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String,
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))

            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListings.map { it.toCompanyListing() }
            ))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldLoadFromCache = !isDbEmpty && !fetchFromRemote

            if (shouldLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try {
                val response = api.getListings()
                companyListingParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(StringHandler.ResourceString(R.string.error_loading_data)))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(StringHandler.ResourceString(R.string.error_loading_data)))
                null
            }

            remoteListings?.let { listings ->
                dao.clearCompanyListings()
                dao.insertCompanyListings(listings.map { it.toCompanyListingEntity() })
                emit(Resource.Success(
                    dao.searchCompanyListing("")
                        .map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(false))
            }

        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val result = api.getCompanyInfo(symbol)
            Resource.Success(result.toCompanyInfo())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = StringHandler.ResourceString(R.string.error_loading_company_info)
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = StringHandler.ResourceString(R.string.error_loading_company_info)
            )
        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = api.getIntradayInfo(symbol)
            val results = intradayInfoParser.parse(response.byteStream())
            Resource.Success(results)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(
                message = StringHandler.ResourceString(R.string.error_loading_intraday)
            )
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(
                message = StringHandler.ResourceString(R.string.error_loading_intraday)
            )
        }
    }
}