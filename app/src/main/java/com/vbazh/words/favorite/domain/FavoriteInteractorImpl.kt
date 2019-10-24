package com.vbazh.words.favorite.domain

import com.vbazh.words.data.local.entity.TranslateEntity
import com.vbazh.words.favorite.presentation.IFavoriteInteractor
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class FavoriteInteractorImpl @Inject constructor(
    private val favoriteRepository: IFavoriteRepository
) : IFavoriteInteractor {

    override fun observeFavorite(): Flowable<List<TranslateEntity>> {
        return favoriteRepository.observeFavorite()
    }

    override fun search(text: String): Flowable<List<TranslateEntity>> {
        return favoriteRepository.search(text)
    }

    override fun removeFromFavorite(translateEntity: TranslateEntity): Completable {
        return favoriteRepository.removeFromFavorite(translateEntity.copy(isFavorite = 0))
    }
}