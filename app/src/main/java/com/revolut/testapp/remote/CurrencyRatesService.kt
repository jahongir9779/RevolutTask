package com.revolut.testapp.remote

import com.revolut.testapp.data.CurrencyRatesModel
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Defines the abstract methods used for interacting with the CurrencyRates API
 */
interface CurrencyRatesService {


    @Headers("Content-Type:application/json", "Accept: application/json")
    @GET("/api/android/latest?base=EUR")
    fun getCurrencyRates(): Single<CurrencyRatesModel>

}
