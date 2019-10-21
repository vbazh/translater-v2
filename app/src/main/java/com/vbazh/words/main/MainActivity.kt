package com.vbazh.words.main

import android.os.Bundle
import com.vbazh.words.R
import com.vbazh.words.di.ComponentManager
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.commands.Replace
import com.vbazh.words.navigation.Screens


class MainActivity : MvpAppCompatActivity(), MainView {

    private val layoutResId = R.layout.activity_main

    @Inject
    lateinit var daggerPresenter: MainPresenter

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter = daggerPresenter

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = object : SupportAppNavigator(this, R.id.mainContainer) {
        override fun applyCommands(commands: Array<Command>) {
            super.applyCommands(commands)
            supportFragmentManager.executePendingTransactions()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        ComponentManager.mainComponent.getInstance().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)

        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf(Replace(Screens.Translate)))
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        if (isFinishing) ComponentManager.mainComponent.destroy()
        super.onDestroy()
    }
}
