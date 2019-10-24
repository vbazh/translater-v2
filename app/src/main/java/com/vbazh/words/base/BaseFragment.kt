package com.vbazh.words.base

import android.os.Bundle
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import moxy.MvpAppCompatFragment

abstract class BaseFragment : MvpAppCompatFragment() {

    abstract val layoutResId: Int

    abstract fun injectComponent()

    abstract fun destroyComponent()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        injectComponent()
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.disposeIfNeed()
        if (needDestroyComponent()) {
            destroyComponent()
        }
    }

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private fun needDestroyComponent(): Boolean {
        return when {
            activity?.isChangingConfigurations == true -> false
            activity?.isFinishing == true -> true
            else -> true
        }
    }
}