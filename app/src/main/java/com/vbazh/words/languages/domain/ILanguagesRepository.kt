package com.vbazh.words.languages.domain

import com.vbazh.words.data.local.entity.LanguageEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface ILanguagesRepository {

    fun getLanguages(uiLang: String): Single<List<LanguageEntity>>

    fun setSourceLanguage(source: String): Completable

    fun setTargetLanguage(target: String): Completable
}