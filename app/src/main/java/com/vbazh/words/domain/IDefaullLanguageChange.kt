package com.vbazh.words.domain

import com.vbazh.words.translate.pick.data.Language
import io.reactivex.Observable

interface IDefaullLanguageChange {

    fun observeDefaultSourceLanguage(): Observable<Language>

    fun observeDefaultTargetLanguage(): Observable<Language>
}