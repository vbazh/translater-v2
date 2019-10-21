package com.vbazh.words.translate.translate.data

import com.f2prateek.rx.preferences2.RxSharedPreferences
import com.vbazh.words.data.DataConsts
import com.vbazh.words.data.local.AppDatabase
import com.vbazh.words.data.local.entity.LanguageEntity
import com.vbazh.words.data.local.entity.TranslateEntity
import com.vbazh.words.data.remote.ApiService
import com.vbazh.words.data.remote.response.TranslateResponse
import com.vbazh.words.translate.translate.domain.ITranslateRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class TranslateRepositoryImpl @Inject constructor(
    val api: ApiService,
    private val database: AppDatabase,
    private val rxSharedPreferences: RxSharedPreferences
) :
    ITranslateRepository {

    var source: String? = null
    var target: String? = null

    override fun getSourceLanguage(): Observable<LanguageEntity> {
        return rxSharedPreferences.getString(DataConsts.PREFERENCES_SOURCE_LANG, "-")
            .asObservable()
            .switchMap { id ->
                source = id
                database.languageDao().getById(id)
            }
    }

    override fun getTargetLanguage(): Observable<LanguageEntity> {
        return rxSharedPreferences.getString(DataConsts.PREFERENCES_TARGET_LANG, "-")
            .asObservable()
            .switchMap { id ->
                target = id
                database.languageDao().getById(id)
            }
    }

    override fun translate(text: String): Single<TranslateEntity> {
        return api.translate(text = text, lang = "$source-$target")
            .map { translateResponse ->
                TranslateEntity(
                    target = translateResponse.text[0],
                    direction = translateResponse.lang,
                    source = text
                )
            }
            .doAfterSuccess { translateResponse ->
                database.translateDao().insertAll(translateResponse)
            }
    }
}