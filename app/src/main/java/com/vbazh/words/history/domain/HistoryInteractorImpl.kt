package com.vbazh.words.history.domain

import com.vbazh.words.data.local.entity.TranslateEntity
import com.vbazh.words.history.presentation.IHistoryInteractor
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class HistoryInteractorImpl @Inject constructor(private val historyRepository: IHistoryRepository) :
    IHistoryInteractor {
    override fun observeHistory(): Flowable<List<TranslateEntity>> {
        return historyRepository.observeHistory()
    }

    override fun delete(translateEntity: TranslateEntity): Completable {
        return historyRepository.delete(translateEntity)
    }


}