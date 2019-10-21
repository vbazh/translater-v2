package com.vbazh.words.translate.translate.presentation

import com.vbazh.words.navigation.Screens
import com.vbazh.words.translate.translate.TranslateConsts
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class TranslatePresenter @Inject constructor(
    private val translateInteractor: ITranslateInteractor,
    private val router: Router
) :
    MvpPresenter<TranslateView>() {

    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        compositeDisposable.add(
            translateInteractor.getSourceLanguage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ viewState.setSourceLanguageText(it.name) }, {})
        )

        compositeDisposable.add(
            translateInteractor.getTargetLanguage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ viewState.setTargetLanguageText(it.name) }, {})
        )
    }

    fun pickSourceLanguage() {
        router.navigateTo(Screens.PickLanguage(TranslateConsts.PICK_LANG_SOURCE))
    }

    fun pickTargetLanguage() {
        router.navigateTo(Screens.PickLanguage(TranslateConsts.PICK_LANG_TARGET))
    }

    fun translate(text: String) {

        if (text.isEmpty()) {
            viewState.clearTextSource()
            return
        }

        compositeDisposable.add(
            translateInteractor.translate(text)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    viewState.setTranslateResult(it.target)
                }, {})
        )
    }

    fun navigateToHistory() {
        router.navigateTo(Screens.History)
    }
}