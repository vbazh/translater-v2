package com.vbazh.words.di.domain

import com.vbazh.words.domain.DefaultLanguageChange
import com.vbazh.words.domain.IDefaullLanguageChange
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun defaultLanguageInteractor(): IDefaullLanguageChange {
        return DefaultLanguageChange()
    }
}