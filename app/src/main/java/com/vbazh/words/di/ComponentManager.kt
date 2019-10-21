package com.vbazh.words.di

import android.content.Context
import com.vbazh.words.di.app.AppComponent
import com.vbazh.words.di.app.AppModule
import com.vbazh.words.di.app.DaggerAppComponent

val ComponentManager: ComponentManagerImpl by lazy { ComponentManagerImpl() }

class ComponentManagerImpl {

    private lateinit var appComponent: AppComponent

    fun initAppComponent(context: Context) {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(context))
            .build()
    }

    val mainComponent =
        Component { getAppComponent().mainComponentBuilder().build() }

    val pickLanguageComponent =
        Component { getAppComponent().pickLanguageComponentBuilder().build() }

    val translateComponent =
        Component { getAppComponent().translateComponentBuilder().build() }

    val historyComponent = Component { getAppComponent().historyComponentBuilder().build() }

    fun getAppComponent() = appComponent

    class Component<T>(private val builder: () -> T) {
        private var instance: T? = null

        fun getInstance() = instance ?: builder().also { instance = it }

        fun newInstance() = builder().also { instance = it }

        fun destroy() {
            instance = null
        }
    }
}