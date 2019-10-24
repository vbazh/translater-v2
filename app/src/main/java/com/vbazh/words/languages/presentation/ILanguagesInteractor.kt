package com.vbazh.words.languages.presentation

import com.vbazh.words.data.local.entity.LanguageEntity
import io.reactivex.Completable
import io.reactivex.Single

interface ILanguagesInteractor {

    fun getLanguage(ui: String): Single<List<LanguageEntity>>

    fun chooseSourceLanguage(source: String): Completable

    fun chooseTargetLanguage(target: String): Completable
}