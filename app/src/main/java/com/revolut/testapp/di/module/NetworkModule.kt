package com.revolut.testapp.di.module

import com.revolut.testapp.remote.CurrencyRatesService
import com.revolut.remote.CurrencyRatesServiceFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Wasabeef on 2017/10/16.
 */
@Module
open class NetworkModule {

    @Provides
    @Singleton
    fun provideCurrencyRatesService(): CurrencyRatesService =
        CurrencyRatesServiceFactory.makeCurrencyRatesService(true)




}