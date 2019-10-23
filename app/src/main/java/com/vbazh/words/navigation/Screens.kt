package com.vbazh.words.navigation

import com.vbazh.words.favorite.presentation.FavoriteFragment
import com.vbazh.words.history.presentation.HistoryFragment
import com.vbazh.words.translate.translate.presentation.TranslateFragment
import com.vbazh.words.translate.pick.presentation.PickLanguageFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object Translate : SupportAppScreen() {
        override fun getFragment() = TranslateFragment.newInstance()
    }

    data class PickLanguage(val type: String) : SupportAppScreen() {
        override fun getFragment() = PickLanguageFragment.newInstance(type)
    }

    object History : SupportAppScreen() {
        override fun getFragment() = HistoryFragment.newInstance()
    }

    object Favorite : SupportAppScreen() {
        override fun getFragment() = FavoriteFragment.newInstance()
    }
}