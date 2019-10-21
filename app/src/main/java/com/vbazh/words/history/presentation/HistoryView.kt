package com.vbazh.words.history.presentation

import com.vbazh.words.data.local.entity.TranslateEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface HistoryView : MvpView {

    fun setItems(translate: List<TranslateEntity>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun successDelete()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun failedDelete()
}