package com.vbazh.words.favorite.presentation

import android.util.Log
import com.vbazh.words.base.disposeIfNeed
import com.vbazh.words.data.local.entity.TranslateEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class FavoritePresenter @Inject constructor(
    private val favoriteInteractor: IFavoriteInteractor,
    private val router: Router
) : MvpPresenter<FavoriteView>() {

    private val compositeDisposable = CompositeDisposable()
    private var favoriteDisposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getFavorite()
    }

    private fun getFavorite() {
        favoriteDisposable?.disposeIfNeed()

        favoriteDisposable =
            favoriteInteractor.observeFavorite()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showProgress() }
                .subscribe(
                    {
                        setResult(it)
                    },
                    { Log.d("ERROR", "error loading favorites", it) })

        favoriteDisposable?.let { compositeDisposable.add(it) }
    }

    fun removeFromFavorite(translateEntity: TranslateEntity) {
        compositeDisposable.add(
            favoriteInteractor.removeFromFavorite(translateEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showProgress() }
                .doFinally { viewState.hideProgress() }
                .subscribe(
                    { viewState.successDelete() },
                    { viewState.failedDelete() })
        )
    }

    fun navigateBack() {
        router.exit()
    }

    fun search(text: String) {
        if (text.isEmpty()) {
            getFavorite()
            return
        }

        favoriteDisposable?.disposeIfNeed()

        favoriteDisposable = favoriteInteractor.search(text)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showProgress() }
            .subscribe(
                { setResult(it) },
                { Log.d("ERROR", "error search", it) })

        favoriteDisposable?.let { compositeDisposable.add(it) }
    }

    private fun setResult(items: List<TranslateEntity>) {
        viewState.hideProgress()
        if (items.isEmpty()) {
            viewState.showEmptyListText()
        } else {
            viewState.hideEmptyListText()
        }
        viewState.setItems(items)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}