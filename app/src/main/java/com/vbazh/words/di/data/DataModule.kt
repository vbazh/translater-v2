package com.vbazh.words.di.data

import com.vbazh.words.di.data.local.LocalDataModule
import com.vbazh.words.di.data.remote.RemoteDataModule
import dagger.Module

@Module(includes = [LocalDataModule::class, RemoteDataModule::class])
class DataModule {

}