package com.vbazh.words.translate

import com.vbazh.words.translate.presentation.TranslateFragment
import dagger.Subcomponent

@TranslateScope
@Subcomponent(modules = [TranslateModule::class])
interface TranslateComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): TranslateComponent
    }

    fun inject(translateFragment: TranslateFragment)
}