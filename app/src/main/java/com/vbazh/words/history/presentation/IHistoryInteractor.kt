package com.vbazh.words.history.presentation

import com.vbazh.words.data.local.entity.TranslateEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface IHistoryInteractor {

    fun observeHistory(): Flowable<List<TranslateEntity>>

    fun delete(translateEntity: TranslateEntity): Completable

    fun favorite(translateEntity: TranslateEntity): Completable

    fun search(text: String) : Single<List<TranslateEntity>>
}