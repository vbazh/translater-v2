package com.vbazh.words.domain

import com.vbazh.words.translate.pick.data.Language
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class DefaultLanguageChange : IDefaullLanguageChange {

    private val defaultSourceLanguage = BehaviorSubject.create<Language>()
    private val defaultTargetLanguage = BehaviorSubject.create<Language>()

    override fun observeDefaultSourceLanguage(): Observable<Language> {
        return defaultSourceLanguage
    }

    override fun observeDefaultTargetLanguage(): Observable<Language> {
        return defaultTargetLanguage
    }
}