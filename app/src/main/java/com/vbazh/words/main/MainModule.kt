package com.vbazh.words.main

import com.vbazh.words.data.remote.ApiService
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @MainScope
    @Provides
    fun providePresenter(apiService: ApiService): MainPresenter {
        return MainPresenter(apiService)
    }
}