package com.vbazh.words.translate.translate

import com.vbazh.words.translate.translate.presentation.TranslateFragment
import dagger.Subcomponent

@Subcomponent(modules = [TranslateModule::class])
interface TranslateComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): TranslateComponent
    }

    fun inject(translateFragment: TranslateFragment)
}