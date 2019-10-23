package com.vbazh.words.favorite.data

import com.vbazh.words.data.local.AppDatabase
import com.vbazh.words.data.local.entity.TranslateEntity
import com.vbazh.words.favorite.domain.IFavoriteRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(private val database: AppDatabase) :
    IFavoriteRepository {

    override fun observeFavorite(): Flowable<List<TranslateEntity>> {
        return database.translateDao().getFavorites()
    }

    override fun search(text: String): Single<List<TranslateEntity>> {
        return database.translateDao().findFavoriteByText(text)
    }

    override fun removeFromFavorite(translateEntity: TranslateEntity): Completable {
        return database.translateDao().update(translateEntity)
    }
}