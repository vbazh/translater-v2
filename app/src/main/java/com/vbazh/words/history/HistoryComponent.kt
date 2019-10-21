package com.vbazh.words.history

import com.vbazh.words.history.presentation.HistoryFragment
import dagger.Subcomponent

@Subcomponent(modules = [HistoryModule::class])
interface HistoryComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): HistoryComponent
    }

    fun inject(historyFragment: HistoryFragment)
}