package com.vbazh.words.languages

import android.content.SharedPreferences
import com.vbazh.words.data.local.AppDatabase
import com.vbazh.words.data.remote.ApiService
import com.vbazh.words.languages.data.LanguagesRepositoryImpl
import com.vbazh.words.languages.domain.LanguagesInteractorImpl
import com.vbazh.words.languages.domain.ILanguagesRepository
import com.vbazh.words.languages.presentation.ILanguagesInteractor
import com.vbazh.words.languages.presentation.PickLanguagePresenter
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class PickLanguageModule {

    @PickLanguageScope
    @Provides
    fun providePresenter(
        languagesInteractor: ILanguagesInteractor,
        router: Router
    ): PickLanguagePresenter {
        return PickLanguagePresenter(languagesInteractor, router)
    }

    @PickLanguageScope
    @Provides
    fun provideInteractor(languagesRepository: ILanguagesRepository): ILanguagesInteractor {
        return LanguagesInteractorImpl(languagesRepository)
    }

    @PickLanguageScope
    @Provides
    fun provideRepository(
        apiService: ApiService,
        database: AppDatabase,
        sharedPreferences: SharedPreferences
    ): ILanguagesRepository {
        return LanguagesRepositoryImpl(apiService, database, sharedPreferences)
    }
}