package com.vbazh.words.translate.translate.presentation

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface TranslateView : MvpView {

    fun setTargetLanguageText(target: String)

    fun setSourceLanguageText(source: String)

    fun setTranslateResult(translated: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showErrorPickLanguage()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun clearTextSource()

    fun setTargetLanguageEmpty()

    fun setSourceLanguageEmpty()
}