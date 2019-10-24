package com.vbazh.words.translate.presentation

import android.util.Log
import com.vbazh.words.navigation.Screens
import com.vbazh.words.translate.TranslateConsts
import com.vbazh.words.translate.data.LangDidntPickedException
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

    private var cachedText = ""

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        compositeDisposable.add(
            translateInteractor.getSourceLanguage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ defaultSource ->
                    if (defaultSource.id.isEmpty()) {
                        viewState.setSourceLanguageEmpty()
                    } else {
                        viewState.setSourceLanguageText(defaultSource.name)
                        translate(cachedText)
                    }
                }, {})
        )

        compositeDisposable.add(
            translateInteractor.getTargetLanguage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ defaultTarget ->
                    if (defaultTarget.id.isEmpty()) {
                        viewState.setTargetLanguageEmpty()
                    } else {
                        viewState.setTargetLanguageText(defaultTarget.name)
                        translate(cachedText)
                    }
                }, {})
        )
    }

    fun pickSourceLanguage() {
        router.navigateTo(Screens.PickLanguage(TranslateConsts.PICK_LANG_SOURCE))
    }

    fun pickTargetLanguage() {
        router.navigateTo(Screens.PickLanguage(TranslateConsts.PICK_LANG_TARGET))
    }

    fun translate(text: String) {
        cachedText = text
        if (text.isEmpty() ) {
            viewState.clearTextSource()
            return
        }

        compositeDisposable.add(
            translateInteractor.translate(text)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { viewState.setTranslateResult(it.target) },
                    {
                        Log.d("ERROR", "error translate", it)
                        if (it is LangDidntPickedException) {
                            viewState.showErrorPickLanguage()
                        }
                    })
        )
    }

    fun clearTranslation() {
        viewState.clearTextSource()
    }

    fun navigateToHistory() {
        router.navigateTo(Screens.History)
    }

    fun navigateToFavorite() {
        router.navigateTo(Screens.Favorite)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}