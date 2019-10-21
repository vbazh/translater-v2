package com.vbazh.words.di.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vbazh.words.BuildConfig
import com.vbazh.words.data.remote.ApiService
import com.vbazh.words.data.remote.interceptors.QueryInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RemoteDataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        queryInterceptor: QueryInterceptor
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        httpClient.addInterceptor(queryInterceptor)

        return httpClient.build()
    }

    @Provides
    @Singleton
    fun provideTeletypeRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideQueryInterceptor() = QueryInterceptor()
}