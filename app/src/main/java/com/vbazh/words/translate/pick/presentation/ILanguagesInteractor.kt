package com.vbazh.words.translate.pick.presentation

import com.vbazh.words.data.local.entity.LanguageEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface ILanguagesInteractor {

    fun getLanguage(): Flowable<List<LanguageEntity>>

    fun chooseSourceLanguage(source: String): Completable

    fun chooseTargetLanguage(target: String): Completable
}