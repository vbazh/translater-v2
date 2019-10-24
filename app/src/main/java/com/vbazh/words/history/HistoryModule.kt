package com.vbazh.words.history

import com.vbazh.words.data.local.AppDatabase
import com.vbazh.words.data.remote.ApiService
import com.vbazh.words.history.data.HistoryRepositoryImpl
import com.vbazh.words.history.domain.HistoryInteractorImpl
import com.vbazh.words.history.domain.IHistoryRepository
import com.vbazh.words.history.presentation.HistoryPresenter
import com.vbazh.words.history.presentation.IHistoryInteractor
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class HistoryModule {

    @HistoryScope
    @Provides
    fun providePresenter(translateInteractor: IHistoryInteractor, router: Router) =
        HistoryPresenter(translateInteractor, router)

    @HistoryScope
    @Provides
    fun provideInteractor(
        historyRepository: IHistoryRepository
    ): IHistoryInteractor {
        return HistoryInteractorImpl(historyRepository)
    }

    @HistoryScope
    @Provides
    fun provideRepository(
        api: ApiService,
        database: AppDatabase
    ): IHistoryRepository {
        return HistoryRepositoryImpl(api, database)
    }
}