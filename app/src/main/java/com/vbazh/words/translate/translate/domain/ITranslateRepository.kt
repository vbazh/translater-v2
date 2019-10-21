package com.vbazh.words.translate.translate.domain

import com.vbazh.words.data.local.entity.LanguageEntity
import com.vbazh.words.data.local.entity.TranslateEntity
import com.vbazh.words.data.remote.response.TranslateResponse
import io.reactivex.Observable
import io.reactivex.Single

interface ITranslateRepository {

    fun getSourceLanguage(): Observable<LanguageEntity>

    fun getTargetLanguage(): Observable<LanguageEntity>

    fun translate(text: String): Single<TranslateEntity>
}