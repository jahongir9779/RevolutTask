package com.revolut.testapp.base


import androidx.lifecycle.ViewModel
import com.revolut.testapp.MainViewModel
import com.revolut.testapp.di.component.DaggerViewModelInjector
import com.revolut.testapp.di.component.ViewModelInjector
import com.revolut.testapp.di.module.NetworkModule

abstract class BaseViewModel : ViewModel() {

    private val injector: ViewModelInjector = DaggerViewModelInjector
            .builder()
            .networkModule(NetworkModule())
            .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is MainViewModel -> injector.inject(this)
        }
    }
}