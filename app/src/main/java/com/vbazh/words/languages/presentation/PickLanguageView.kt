package com.vbazh.words.languages.presentation

import com.vbazh.words.data.local.entity.LanguageEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface PickLanguageView : MvpView {

    fun setItems(languages: List<LanguageEntity>)

    fun showProgress()

    fun hideProgress()

    fun showError(error: String)
}