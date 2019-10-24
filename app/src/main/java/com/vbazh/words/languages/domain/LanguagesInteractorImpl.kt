package com.vbazh.words.languages.domain

import com.vbazh.words.data.local.entity.LanguageEntity
import com.vbazh.words.languages.presentation.ILanguagesInteractor
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class LanguagesInteractorImpl @Inject constructor(
    private val languagesRepository: ILanguagesRepository
) : ILanguagesInteractor {

    override fun chooseSourceLanguage(source: String): Completable {
        return languagesRepository.setSourceLanguage(source)
    }

    override fun chooseTargetLanguage(target: String): Completable {
        return languagesRepository.setTargetLanguage(target)
    }

    override fun getLanguage(ui: String): Single<List<LanguageEntity>> {
        return languagesRepository.getLanguages(ui)
    }

}