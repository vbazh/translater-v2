package com.vbazh.words.translate.translate

import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.vbazh.words.data.local.AppDatabase
import com.vbazh.words.data.remote.ApiService
import com.vbazh.words.domain.IDefaullLanguageChange
import com.vbazh.words.translate.translate.data.TranslateRepositoryImpl
import com.vbazh.words.translate.translate.domain.ITranslateRepository
import com.vbazh.words.translate.translate.domain.TranslateInteractorImpl
import com.vbazh.words.translate.translate.presentation.ITranslateInteractor
import com.vbazh.words.translate.translate.presentation.TranslatePresenter
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class TranslateModule {

    @Provides
    fun providePresenter(translateInteractor: ITranslateInteractor, router: Router) =
        TranslatePresenter(translateInteractor, router)

    @Provides
    fun provideInteractor(
        translateRepository: ITranslateRepository,
        defaultLanguageChange: IDefaullLanguageChange
    ): ITranslateInteractor {
        return TranslateInteractorImpl(defaultLanguageChange, translateRepository)
    }

    @Provides
    fun provideRepository(
        api: ApiService,
        database: AppDatabase,
        rxSharedPreferences: RxSharedPreferences
    ): ITranslateRepository {
        return TranslateRepositoryImpl(api, database, rxSharedPreferences)
    }
}