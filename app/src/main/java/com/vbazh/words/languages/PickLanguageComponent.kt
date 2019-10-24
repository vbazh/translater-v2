package com.vbazh.words.languages

import com.vbazh.words.languages.presentation.PickLanguageFragment
import dagger.Subcomponent

@PickLanguageScope
@Subcomponent(modules = [PickLanguageModule::class])
interface PickLanguageComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): PickLanguageComponent
    }

    fun inject(pickLanguageFragment: PickLanguageFragment)
}