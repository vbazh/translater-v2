package com.vbazh.words.languages.data

import android.content.SharedPreferences
import com.vbazh.words.data.DataConsts
import com.vbazh.words.data.local.AppDatabase
import com.vbazh.words.data.local.entity.LanguageEntity
import com.vbazh.words.data.remote.ApiService
import com.vbazh.words.languages.domain.ILanguagesRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LanguagesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val database: AppDatabase,
    private val sharedPreferences: SharedPreferences
) : ILanguagesRepository {

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

    override fun getLanguages(uiLang: String): Single<List<LanguageEntity>> {
        return database.languageDao().getAll()
            .filter { it.isNotEmpty() }
            .switchIfEmpty(getLanguagesFromNetwork(uiLang))
    }

    private fun getLanguagesFromNetwork(ui: String): Single<List<LanguageEntity>> {
        return apiService.getLanguages(ui)
            .map { response ->
                response.languages.map { entry ->
                    LanguageEntity(
                        id = entry.key,
                        name = entry.value
                    )
                }
            }
            .doOnSuccess { langs -> database.languageDao().insertAll(langs) }
    }
}