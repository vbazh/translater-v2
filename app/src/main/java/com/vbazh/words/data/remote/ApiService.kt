package com.vbazh.words.data.remote

import com.vbazh.words.data.remote.response.LanguagesResponse
import com.vbazh.words.data.remote.response.TranslateResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v1.5/tr.json/getLangs")
    fun getLanguages(@Query("ui") ui: String): Single<LanguagesResponse>

    @GET("v1.5/tr.json/translate")
    fun translate(
        @Query("text") text: String,
        @Query("lang") lang: String
    ): Single<TranslateResponse>
}