package com.revolut.testapp

import android.provider.Settings
import android.util.TypedValue
import androidx.multidex.BuildConfig
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.HasActivityInjector
import android.content.Intent
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.revolut.testapp.di.component.AppComponent
import com.revolut.testapp.di.component.DaggerAppComponent
import com.revolut.testapp.di.module.AppModule
import com.revolut.testapp.di.module.NetworkModule


/**
 * Created by jahon on 13-Mar-18.
 */

open class App : DaggerApplication(), HasActivityInjector {


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        component = DaggerAppComponent.builder().application(this)
            .appModule(AppModule(this))
            .netModule(NetworkModule())
            .context(this)
            .build()
        component.inject(this)
        return component
    }


    override fun onCreate() {
        super.onCreate()

        instance = this

    }


    companion object {
        lateinit var component: AppComponent
        lateinit var instance: App private set
    }

}
