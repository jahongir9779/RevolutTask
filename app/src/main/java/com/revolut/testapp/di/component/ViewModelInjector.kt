package com.revolut.testapp.di.component

import com.revolut.testapp.MainViewModel
import com.revolut.testapp.di.module.NetworkModule
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified PostListViewModel.
     * @param postListViewModel PostListViewModel in which to inject the dependencies
     */
    fun inject(mainViewModel: MainViewModel)


    @Component.Builder
    interface Builder {

        fun build(): ViewModelInjector
        fun networkModule(networkModule: NetworkModule): Builder

    }
}