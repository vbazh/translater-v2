package com.vbazh.words.history.presentation

import com.vbazh.words.data.local.entity.TranslateEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface IHistoryInteractor {

    fun observeHistory(): Flowable<List<TranslateEntity>>

    fun delete(translateEntity: TranslateEntity): Completable
}