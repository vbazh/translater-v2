package com.vbazh.words.translate.pick.data

import android.content.SharedPreferences
import android.util.Log
import com.vbazh.words.data.DataConsts
import com.vbazh.words.data.local.AppDatabase
import com.vbazh.words.data.local.entity.LanguageEntity
import com.vbazh.words.data.remote.ApiService
import com.vbazh.words.translate.pick.domain.ILanguagesRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LanguagesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val database: AppDatabase,
    private val sharedPreferences: SharedPreferences
) : ILanguagesRepository {

    private val compositeDisposable = CompositeDisposable()

    override fun loadLanguages() {
    }

    override fun setTargetLanguage(target: String): Completable {
        return Completable.fromCallable {
            sharedPreferences.edit().putString(DataConsts.PREFERENCES_TARGET_LANG, target).apply()
        }
    }

    override fun setSourceLanguage(source: String): Completable {
        return Completable.fromCallable {
            sharedPreferences.edit().putString(DataConsts.PREFERENCES_SOURCE_LANG, source).apply()
        }
    }

    override fun getLanguages(): Flowable<List<LanguageEntity>> {
        compositeDisposable.add(apiService.getLanguages("ru")
            .map { response ->
                response.languages.map { entry -> LanguageEntity(id = entry.key, name = entry.value) }
            }
            .subscribeOn(Schedulers.io())
            .subscribe({ langs ->
                database.languageDao().insertAll(langs)
            }, {})
        )

        return database.languageDao().getAll()
    }
}