package com.vbazh.words.favorite.presentation

import com.vbazh.words.data.local.entity.TranslateEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface FavoriteView : MvpView {

    fun setItems(favorites: List<TranslateEntity>)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun successDelete()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun failedDelete()

    fun showProgress()

    fun hideProgress()

    fun showEmptyListText()

    fun hideEmptyListText()
}