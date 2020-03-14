package com.revolut.testapp.di.module

//import com.revolut.testapp.UiThread
//import com.revolut.remote.CurrencyRatesServiceFactory
import com.revolut.testapp.App
import com.revolut.testapp.BuildConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

//@Singleton

@Module(includes = [ViewModelModule::class])
class AppModule(private val baseApp: App) {

//    @Provides
//    @Singleton
//    @PerApplication
//    fun provideApplicationContext(): Context {
//        return baseApp
//    }

//    @Provides
//    @PerApplication
//    fun provideContext(application: Application): Context {
//        return application
//    }


}