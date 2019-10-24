package com.vbazh.words.translate.domain

import com.vbazh.words.data.local.entity.LanguageEntity
import com.vbazh.words.data.local.entity.TranslateEntity
import com.vbazh.words.domain.IDefaullLanguageChange
import com.vbazh.words.translate.presentation.ITranslateInteractor
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class TranslateInteractorImpl @Inject constructor(
    val defaultLanguageChange: IDefaullLanguageChange,
    private val translateRepository: ITranslateRepository
) :
    ITranslateInteractor {

    override fun getSourceLanguage(): Observable<LanguageEntity> {
        return translateRepository.getSourceLanguage()
    }

    override fun getTargetLanguage(): Observable<LanguageEntity> {
        return translateRepository.getTargetLanguage()
    }

    override fun translate(text: String): Single<TranslateEntity> {
        return translateRepository.translate(text)
    }
}