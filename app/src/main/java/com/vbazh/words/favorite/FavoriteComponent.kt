package com.vbazh.words.favorite

import com.vbazh.words.favorite.presentation.FavoriteFragment
import dagger.Subcomponent

@FavoriteScope
@Subcomponent(modules = [FavoriteModule::class])
interface FavoriteComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): FavoriteComponent
    }

    fun inject(fragment: FavoriteFragment)
}