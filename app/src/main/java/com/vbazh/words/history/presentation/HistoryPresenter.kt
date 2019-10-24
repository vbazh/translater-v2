package com.vbazh.words.history.presentation

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
class HistoryPresenter @Inject constructor(
    private val historyInteractor: IHistoryInteractor,
    private val router: Router
) :
    MvpPresenter<HistoryView>() {

    private val compositeDisposable = CompositeDisposable()
    private var translatesDisposable: Disposable? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getHistory()
    }

    private fun getHistory() {
        translatesDisposable?.disposeIfNeed()

        translatesDisposable =
            historyInteractor.observeHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showProgress() }
                .subscribe(
                    { setResult(it) },
                    { Log.d("ERROR", "error loading history", it) })
    }

    fun deleteTranslate(translateEntity: TranslateEntity) {
        compositeDisposable.add(
            historyInteractor.delete(translateEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showProgress() }
                .doFinally { viewState.hideProgress() }
                .subscribe(
                    { viewState.successDelete() },
                    { viewState.failedDelete() })
        )
    }

    fun favoriteTranslate(translateEntity: TranslateEntity) {
        compositeDisposable.add(
            historyInteractor.favorite(translateEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showProgress() }
                .doFinally { viewState.hideProgress() }
                .subscribe(
                    { },
                    { Log.d("ERROR", "error favorite", it) })
        )
    }

    fun navigateBack() {
        router.exit()
    }

    fun search(text: String) {
        if (text.isEmpty()) {
            getHistory()
            return
        }

        translatesDisposable?.disposeIfNeed()

        translatesDisposable = historyInteractor.search(text)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.showProgress() }
            .subscribe(
                { setResult(it) },
                { Log.d("ERROR", "error search", it) })

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