package com.vbazh.words.history.domain

import com.vbazh.words.data.local.entity.TranslateEntity
import com.vbazh.words.history.presentation.IHistoryInteractor
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class HistoryInteractorImpl @Inject constructor(private val historyRepository: IHistoryRepository) :
    IHistoryInteractor {

    override fun observeHistory(): Flowable<List<TranslateEntity>> {
        return historyRepository.observeHistory()
    }

    override fun delete(translateEntity: TranslateEntity): Completable {
        return historyRepository.delete(translateEntity)
    }

    override fun search(text: String): Flowable<List<TranslateEntity>> {
        return historyRepository.search(text)
    }

    override fun favorite(translateEntity: TranslateEntity): Completable {
        return historyRepository.favorite(changeIsFavorite(translateEntity))
    }

    private fun changeIsFavorite(translateEntity: TranslateEntity): TranslateEntity {
        val isFavorite = when (translateEntity.isFavorite) {
            0 -> 1
            1 -> 0
            else -> 0
        }
        return translateEntity.copy(isFavorite = isFavorite)
    }
}