package com.vbazh.words.favorite

import com.vbazh.words.data.local.AppDatabase
import com.vbazh.words.favorite.data.FavoriteRepositoryImpl
import com.vbazh.words.favorite.domain.FavoriteInteractorImpl
import com.vbazh.words.favorite.domain.IFavoriteRepository
import com.vbazh.words.favorite.presentation.FavoritePresenter
import com.vbazh.words.favorite.presentation.IFavoriteInteractor
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class FavoriteModule {

    @FavoriteScope
    @Provides
    fun providePresenter(
        favoriteInteractor: IFavoriteInteractor,
        router: Router
    ): FavoritePresenter {
        return FavoritePresenter(favoriteInteractor, router)
    }

    @FavoriteScope
    @Provides
    fun provideInteractor(favoriteRepository: IFavoriteRepository): IFavoriteInteractor {
        return FavoriteInteractorImpl(favoriteRepository)
    }

    @FavoriteScope
    @Provides
    fun provideRepository(database: AppDatabase): IFavoriteRepository {
        return FavoriteRepositoryImpl(database)
    }
}