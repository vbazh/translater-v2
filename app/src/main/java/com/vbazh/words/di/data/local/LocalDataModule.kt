package com.vbazh.words.di.data.local

import android.content.Context
import android.content.SharedPreferences
import com.vbazh.words.data.DataConsts
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import com.vbazh.words.data.local.AppDatabase
import androidx.room.Room
import com.f2prateek.rx.preferences2.RxSharedPreferences

@Module
class LocalDataModule {

    @Singleton
    @Provides
    fun providePreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(DataConsts.PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideRxPreferences(preferences: SharedPreferences): RxSharedPreferences {
        return RxSharedPreferences.create(preferences)
    }

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DataConsts.DB_NAME).build()
    }
}


