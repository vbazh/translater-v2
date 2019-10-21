package com.vbazh.words.history.presentation

import com.vbazh.words.data.local.entity.TranslateEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
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

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getHistory()
    }

    private fun getHistory() {
        compositeDisposable.add(
            historyInteractor.observeHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.setItems(it)
                }, {})
        )
    }

    fun deleteTranslate(translateEntity: TranslateEntity) {
        compositeDisposable.add(
            historyInteractor.delete(translateEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
            getHistory()
            return
        }

        compositeDisposable.add(
            historyInteractor.search(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ viewState.setItems(it) }, {})
        )
    }
}