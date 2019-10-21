package com.vbazh.words.translate.pick

import com.vbazh.words.translate.pick.presentation.PickLanguageFragment
import dagger.Subcomponent

@Subcomponent(modules = [PickLanguageModule::class])
interface PickLanguageComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): PickLanguageComponent
    }

    fun inject(pickLanguageFragment: PickLanguageFragment)
}