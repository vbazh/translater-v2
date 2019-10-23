package com.vbazh.words.di.app

import com.vbazh.words.di.data.DataModule
import com.vbazh.words.di.domain.DomainModule
import com.vbazh.words.main.MainComponent
import com.vbazh.words.di.navigation.NavigationModule
import com.vbazh.words.favorite.FavoriteComponent
import com.vbazh.words.history.HistoryComponent
import com.vbazh.words.translate.pick.PickLanguageComponent
import com.vbazh.words.translate.translate.TranslateComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        DataModule::class,
        NavigationModule::class,
        DomainModule::class]
)
interface AppComponent {

    fun mainComponentBuilder(): MainComponent.Builder

    fun pickLanguageComponentBuilder(): PickLanguageComponent.Builder

    fun translateComponentBuilder(): TranslateComponent.Builder

    fun historyComponentBuilder(): HistoryComponent.Builder

    fun favoriteComponentBuilder(): FavoriteComponent.Builder
}