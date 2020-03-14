package com.revolut.remote

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.revolut.testapp.remote.CurrencyRatesService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Provide "make" methods to create instances of [CurrencyRatesService]
 * and its related dependencies, such as OkHttpClient, Gson, etc.
 */
object CurrencyRatesServiceFactory {

    fun makeCurrencyRatesService(isDebug: Boolean): CurrencyRatesService {
        val okHttpClient = makeOkHttpClient(
                makeLoggingInterceptor(isDebug))
        return makeCurrencyRatesService(okHttpClient, makeGson())
    }

    private fun makeCurrencyRatesService(okHttpClient: OkHttpClient, gson: Gson): CurrencyRatesService {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://hiring.revolut.codes")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        return retrofit.create(CurrencyRatesService::class.java)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
    }

    private fun makeGson(): Gson {
        return GsonBuilder()
                .setLenient()
                .create()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug)
            HttpLoggingInterceptor.Level.BODY
        else
          HttpLoggingInterceptor.Level.NONE
        return logging
    }

}