package com.vbazh.words.translate

import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.vbazh.words.data.local.AppDatabase
import com.vbazh.words.data.remote.ApiService
import com.vbazh.words.domain.IDefaullLanguageChange
import com.vbazh.words.translate.data.TranslateRepositoryImpl
import com.vbazh.words.translate.domain.ITranslateRepository
import com.vbazh.words.translate.domain.TranslateInteractorImpl
import com.vbazh.words.translate.presentation.ITranslateInteractor
import com.vbazh.words.translate.presentation.TranslatePresenter
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class TranslateModule {

    @TranslateScope
    @Provides
    fun providePresenter(translateInteractor: ITranslateInteractor, router: Router) =
        TranslatePresenter(translateInteractor, router)

    @TranslateScope
    @Provides
    fun provideInteractor(
        translateRepository: ITranslateRepository,
        defaultLanguageChange: IDefaullLanguageChange
    ): ITranslateInteractor {
        return TranslateInteractorImpl(defaultLanguageChange, translateRepository)
    }

    @TranslateScope
    @Provides
    fun provideRepository(
        api: ApiService,
        database: AppDatabase,
        rxSharedPreferences: RxSharedPreferences
    ): ITranslateRepository {
        return TranslateRepositoryImpl(api, database, rxSharedPreferences)
    }
}