package lostankit7.droid.stockmarket.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import lostankit7.droid.stockmarket.data.csv.CSVParser
import lostankit7.droid.stockmarket.data.csv.CompanyListingsParser
import lostankit7.droid.stockmarket.data.repository.StockRepositoryImp
import lostankit7.droid.stockmarket.domain.model.CompanyListing
import lostankit7.droid.stockmarket.domain.repository.StockRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingParser(
        companyListingsParser: CompanyListingsParser,
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImp: StockRepositoryImp,
    ): StockRepository
}