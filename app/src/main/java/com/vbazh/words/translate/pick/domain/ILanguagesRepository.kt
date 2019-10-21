package com.vbazh.words.translate.pick.domain

import com.vbazh.words.data.local.entity.LanguageEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface ILanguagesRepository {

    fun loadLanguages()

    fun getLanguages(): Flowable<List<LanguageEntity>>

    fun setSourceLanguage(source: String): Completable

    fun setTargetLanguage(target: String) : Completable
}