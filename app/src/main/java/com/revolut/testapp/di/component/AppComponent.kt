package com.revolut.testapp.di.component

import android.app.Application
import android.content.Context
import com.revolut.testapp.App
import com.revolut.testapp.MainActivity
import com.revolut.testapp.di.module.ActivityBindingModule
import com.revolut.testapp.di.module.AppModule
import com.revolut.testapp.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton


/**
 * Created by Wasabeef on 2017/10/16.
 */
@Singleton
@Component(modules = [NetworkModule::class, AppModule::class, AndroidInjectionModule::class, ActivityBindingModule::class])
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(app: App)
    fun inject(appContext: Context)


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun appModule(appModule: AppModule): Builder

        @BindsInstance
        fun netModule(netModule: NetworkModule): Builder

        fun build(): AppComponent
    }

}