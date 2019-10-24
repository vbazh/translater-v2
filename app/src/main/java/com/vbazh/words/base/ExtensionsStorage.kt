package com.vbazh.words.base

import android.view.View
import io.reactivex.disposables.Disposable

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun Disposable.disposeIfNeed() {
    if (!this.isDisposed) {
        this.dispose()
    }
}