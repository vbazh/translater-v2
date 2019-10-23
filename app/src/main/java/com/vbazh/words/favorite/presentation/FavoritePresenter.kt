package com.vbazh.words.favorite.presentation

import com.vbazh.words.data.local.entity.TranslateEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
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

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getFavorite()
    }

    private fun getFavorite() {
        compositeDisposable.add(
            favoriteInteractor.observeFavorite()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ viewState.setItems(it) }, {})
        )
    }

    fun removeFromFavorite(translateEntity: TranslateEntity) {
        compositeDisposable.add(
            favoriteInteractor.removeFromFavorite(translateEntity)
                .subscribeOn(Schedulers.io())
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

        compositeDisposable.add(
            favoriteInteractor.search(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ viewState.setItems(it) }, {})
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}