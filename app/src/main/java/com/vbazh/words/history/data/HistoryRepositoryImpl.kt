package com.vbazh.words.history.data

import com.vbazh.words.data.local.AppDatabase
import com.vbazh.words.data.local.entity.TranslateEntity
import com.vbazh.words.data.remote.ApiService
import com.vbazh.words.history.domain.IHistoryRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val database: AppDatabase
) : IHistoryRepository {

    override fun observeHistory(): Flowable<List<TranslateEntity>> {
        return database.translateDao().getAll()
    }

    override fun delete(translateEntity: TranslateEntity): Completable {
        return database.translateDao().delete(translateEntity)
    }
}