package com.example.currencyconverter

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ExchangeRateApiService {
    @GET("latest/{base}")
    fun getLatestRates(
        @Path("base") base: String
    ): Call<ExchangeRateResponse>
}
