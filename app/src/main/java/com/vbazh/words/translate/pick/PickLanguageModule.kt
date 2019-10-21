package com.vbazh.words.translate.pick

import android.content.SharedPreferences
import com.vbazh.words.data.local.AppDatabase
import com.vbazh.words.data.remote.ApiService
import com.vbazh.words.translate.pick.data.LanguagesRepositoryImpl
import com.vbazh.words.translate.pick.domain.LanguagesInteractorImpl
import com.vbazh.words.translate.pick.domain.ILanguagesRepository
import com.vbazh.words.translate.pick.presentation.ILanguagesInteractor
import com.vbazh.words.translate.pick.presentation.PickLanguagePresenter
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router

@Module
class PickLanguageModule {

    @Provides
    fun providePresenter(languagesInteractor: ILanguagesInteractor, router: Router): PickLanguagePresenter {
        return PickLanguagePresenter(languagesInteractor, router)
    }

    @Provides
    fun provideInteractor(languagesRepository: ILanguagesRepository): ILanguagesInteractor {
        return LanguagesInteractorImpl(languagesRepository)
    }

    @Provides
    fun provideRepository(
        apiService: ApiService,
        database: AppDatabase,
        sharedPreferences: SharedPreferences
    ): ILanguagesRepository {
        return LanguagesRepositoryImpl(apiService, database, sharedPreferences)
    }
}