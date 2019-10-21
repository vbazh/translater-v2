package com.vbazh.words.main

import com.vbazh.words.data.remote.ApiService
import com.vbazh.words.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    @Provides
    fun providePresenter(apiService: ApiService): MainPresenter {
        return MainPresenter(apiService)
    }
}