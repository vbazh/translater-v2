package com.vbazh.words.translate.presentation

import com.vbazh.words.data.local.entity.LanguageEntity
import com.vbazh.words.data.local.entity.TranslateEntity
import io.reactivex.Observable
import io.reactivex.Single

interface ITranslateInteractor {

    fun getSourceLanguage(): Observable<LanguageEntity>

    fun getTargetLanguage(): Observable<LanguageEntity>

    fun translate(text: String): Single<TranslateEntity>
}