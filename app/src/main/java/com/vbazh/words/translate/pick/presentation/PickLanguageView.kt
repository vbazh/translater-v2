package com.vbazh.words.translate.pick.presentation

import com.vbazh.words.data.local.entity.LanguageEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface PickLanguageView : MvpView {

    fun setItems(languages: List<LanguageEntity>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showProgress()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun hideProgress()
}