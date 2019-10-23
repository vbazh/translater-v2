package com.vbazh.words.favorite.presentation

import com.vbazh.words.data.local.entity.TranslateEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface IFavoriteInteractor {
    fun observeFavorite(): Flowable<List<TranslateEntity>>

    fun search(text: String): Single<List<TranslateEntity>>

    fun removeFromFavorite(translateEntity: TranslateEntity): Completable
}