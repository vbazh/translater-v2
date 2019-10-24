package com.vbazh.words.main

import dagger.Subcomponent

@MainScope
@Subcomponent(modules = [(MainModule::class)])
interface MainComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): MainComponent
    }

    fun inject(mainActivity: MainActivity)
}