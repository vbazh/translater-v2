package com.vbazh.words

import android.app.Application
import com.vbazh.words.di.ComponentManager

class WordsApp: Application() {

    override fun onCreate() {
        super.onCreate()
        ComponentManager.initAppComponent(this)
    }
}