package lostankit7.droid.stockmarket.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import lostankit7.droid.stockmarket.data.local.LocalDataBase
import lostankit7.droid.stockmarket.data.remote.StockApi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockApi(): StockApi {
        return Retrofit.Builder().baseUrl(StockApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create()
    }

    @Provides
    @Singleton
    fun provideLocalDataBase(app: Application): LocalDataBase {
        return Room.databaseBuilder(
            app,
            LocalDataBase::class.java,
            "stock.db"
        ).build()
    }
}