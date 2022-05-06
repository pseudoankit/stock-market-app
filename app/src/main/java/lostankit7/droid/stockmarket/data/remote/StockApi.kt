package lostankit7.droid.stockmarket.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query


interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey: String = API_KEY
    ) : ResponseBody

    companion object {
        const val API_KEY = "PP3Z1V631OHVA4ST"
        const val BASE_URL = "https://alphavantage.co"
    }
}