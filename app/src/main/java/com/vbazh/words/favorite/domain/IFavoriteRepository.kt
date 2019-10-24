package com.vbazh.words.favorite.domain

import com.vbazh.words.data.local.entity.TranslateEntity
import io.reactivex.Completable
import io.reactivex.Flowable

interface IFavoriteRepository {

    fun observeFavorite(): Flowable<List<TranslateEntity>>

    fun search(text: String): Flowable<List<TranslateEntity>>

    fun removeFromFavorite(translateEntity: TranslateEntity): Completable
}